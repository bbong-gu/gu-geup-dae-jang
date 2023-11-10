package com.codesmith.goojangtransfer.transfer.application;

import com.codesmith.goojangtransfer.infra.openvidu.OpenViduClient;
import com.codesmith.goojangtransfer.transfer.dto.message.MeetingJoinMessage;
import com.codesmith.goojangtransfer.transfer.dto.request.TransferCreateRequest;
import com.codesmith.goojangtransfer.transfer.dto.response.MeetingJoinResponse;
import com.codesmith.goojangtransfer.transfer.dto.response.TransferCreateResponse;
import com.codesmith.goojangtransfer.transfer.dto.response.TransferListResponse;
import com.codesmith.goojangtransfer.transfer.dto.response.TransferStatusChangeResponse;
import com.codesmith.goojangtransfer.infra.openfeign.CallingServiceClient;
import com.codesmith.goojangtransfer.infra.openfeign.MemberServiceClient;
import com.codesmith.goojangtransfer.transfer.dto.request.TransferCreateRequest;
import com.codesmith.goojangtransfer.transfer.dto.request.TransferHistoryListRequest;
import com.codesmith.goojangtransfer.transfer.dto.response.*;
import com.codesmith.goojangtransfer.transfer.persistence.TransferRepository;
import com.codesmith.goojangtransfer.transfer.persistence.domain.Status;
import com.codesmith.goojangtransfer.transfer.persistence.domain.Transfer;
import io.openvidu.java.client.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final TransferValidator transferValidator;

    private final OpenViduClient openViduClient;

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MemberServiceClient memberServiceClient;
    private final CallingServiceClient callingServiceClient;

    @Override
    public TransferCreateResponse createTransfer(TransferCreateRequest transferCreateRequest) {
        transferValidator.validateCallingId(transferCreateRequest.getCallingId());
        Transfer transfer = transferRepository.save(new Transfer(transferCreateRequest.getCallingId(), transferCreateRequest.getMemberId(), Status.TRANSFERRING, null));
        return new TransferCreateResponse(transfer.getId(), true);
    }

    @Override
    public TransferStatusChangeResponse completeTransfer(Long transferId) {
        transferValidator.validateTransferId(transferId);
        transferValidator.validateTransferArrive(transferId);
        Transfer transfer = transferRepository.findById(transferId).get();
        transfer.complete();
        transferRepository.save(transfer);
        return new TransferStatusChangeResponse(true);
    }

    @Override
    public TransferStatusChangeResponse cancelTransfer(Long transferId) {
        transferValidator.validateTransferId(transferId);
        transferValidator.validateTransferArrive(transferId);
        Transfer transfer = transferRepository.findById(transferId).get();
        transfer.cancel();
        transferRepository.save(transfer);
        return new TransferStatusChangeResponse(true);
    }

    @Override
    public List<TransferListResponse> getTransferByMember(Long memberId) {
        List<Transfer> transfers = transferRepository.findByMemberIdAndStatus(memberId, Status.TRANSFERRING);

        return transfers.stream()
                .map(transfer -> new TransferListResponse(
                        transfer.getId(),
                        transfer.getCallingId(),
                        transfer.getMemberId(),
                        transfer.getStatus().name(),
                        transfer.getArrivedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public MeetingJoinResponse joinMeeting(Long memberId, Long transferId) {
        Session existSession = openViduClient.checkSession(transferId);
        if (existSession == null) {
            Session session = openViduClient.createSession(transferId);
            MeetingJoinMessage meetingJoinMessage = new MeetingJoinMessage(memberId, transferId);
            simpMessagingTemplate.convertAndSend("/topic/meeting/" + transferId, meetingJoinMessage);
            return new MeetingJoinResponse(openViduClient.getToken(session).getToken());
        }
        return new MeetingJoinResponse(openViduClient.getToken(existSession).getToken());
    }

    @Override
    public void deleteMeeting(Long transferId) {
        openViduClient.closeSession(transferId);
    }

    @Override
    public List<TransferHistoryListResponse> getTransferHistoryList(Long memberId, TransferHistoryListRequest transferHistoryListRequest) {
        SafetyCenterInfoResponse safetyCenterInfoResponse = memberServiceClient.getSafetyCenterInfo(memberId);
        Map<String, String> paramedicMap = safetyCenterInfoResponse.getParamedics().stream()
                .collect(Collectors.toMap(paramedic -> paramedic.getMemberId().toString(), paramedic -> paramedic.getName()));

        List<OccurrenceInfoResponse> occurrenceInfos = callingServiceClient.getOccurrenceInfoList(paramedicMap);

        List<Transfer> transfers = transferRepository.findAllByCallingIds(occurrenceInfos.stream()
                .map(occurrenceInfoResponse -> occurrenceInfoResponse.getCallingId())
                .collect(Collectors.toList()), transferHistoryListRequest.getStartDate(), transferHistoryListRequest.getEndDate());

        List<TransferHistoryListResponse> transferHistoryListResponses = new ArrayList<>();
        for (Transfer transfer: transfers) {
            occurrenceInfos.stream()
                    .filter(occurrenceInfoResponse -> occurrenceInfoResponse.getCallingId().equals(transfer.getCallingId()))
                    .findFirst()
                    .ifPresent(occurrenceInfoResponse -> transferHistoryListResponses.add(new TransferHistoryListResponse(occurrenceInfoResponse, transfer)));
        }

        return transferHistoryListResponses;
    }
}
