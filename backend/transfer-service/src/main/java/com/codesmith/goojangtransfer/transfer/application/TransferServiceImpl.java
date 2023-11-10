package com.codesmith.goojangtransfer.transfer.application;

import com.codesmith.goojangtransfer.infra.openvidu.OpenViduClient;
import com.codesmith.goojangtransfer.transfer.dto.message.JoinMeetingMessage;
import com.codesmith.goojangtransfer.transfer.dto.request.TransferCreateRequest;
import com.codesmith.goojangtransfer.transfer.dto.response.JoinMeetingResponse;
import com.codesmith.goojangtransfer.transfer.dto.response.TransferCreateResponse;
import com.codesmith.goojangtransfer.transfer.dto.response.TransferListResponse;
import com.codesmith.goojangtransfer.transfer.dto.response.TransferStatusChangeResponse;
import com.codesmith.goojangtransfer.transfer.persistence.TransferRepository;
import com.codesmith.goojangtransfer.transfer.persistence.domain.Status;
import com.codesmith.goojangtransfer.transfer.persistence.domain.Transfer;
import io.openvidu.java.client.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final TransferValidator transferValidator;

    private final OpenViduClient openViduClient;

    private final SimpMessagingTemplate simpMessagingTemplate;

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
    public JoinMeetingResponse joinMeeting(Long memberId, Long transferId) {
        Session existSession = openViduClient.isExistSession(transferId);
        if (existSession == null) {
            Session session = openViduClient.createSession(transferId);
            JoinMeetingMessage joinMeetingMessage = new JoinMeetingMessage(memberId, transferId);
            simpMessagingTemplate.convertAndSend("/topic/meeting/" + transferId, joinMeetingMessage);
            return new JoinMeetingResponse(openViduClient.getToken(session).getToken());
        }
        return new JoinMeetingResponse(openViduClient.getToken(existSession).getToken());
    }

    @Override
    public void deleteMeeting(Long transferId) {
        openViduClient.closeSession(transferId);
    }
}
