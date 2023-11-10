package com.codesmith.goojangtransfer.transfer.presentation;

import com.codesmith.goojangtransfer.global.passport.MemberInfo;
import com.codesmith.goojangtransfer.global.passport.presentation.AuthMember;
import com.codesmith.goojangtransfer.transfer.application.TransferService;
import com.codesmith.goojangtransfer.transfer.dto.request.TransferCreateRequest;
import com.codesmith.goojangtransfer.transfer.dto.response.MeetingJoinResponse;
import com.codesmith.goojangtransfer.transfer.dto.request.TransferHistoryListRequest;
import com.codesmith.goojangtransfer.transfer.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;


    @PostMapping
    public ResponseEntity<TransferCreateResponse> createTransfer(@RequestBody TransferCreateRequest transferCreateRequest) {
        return ResponseEntity.ok(transferService.createTransfer(transferCreateRequest));
    }

    @PutMapping("/{transferId}")
    public ResponseEntity<TransferStatusChangeResponse> completeTransfer(@PathVariable Long transferId) {
        return ResponseEntity.ok(transferService.completeTransfer(transferId));
    }

    @DeleteMapping("/{transferId}")
    public ResponseEntity<TransferStatusChangeResponse> cancelTransfer(@PathVariable Long transferId) {
        return ResponseEntity.ok(transferService.cancelTransfer(transferId));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<TransferListResponse>> getTransferByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(transferService.getTransferByMember(memberId));
    }

    @PostMapping("/meeting/{transferId}")
    public ResponseEntity<MeetingJoinResponse> joinMeetingByTransferId(@AuthMember MemberInfo memberInfo, @PathVariable Long transferId) {
        return ResponseEntity.ok(transferService.joinMeeting(memberInfo.getId(), transferId));
    }

    @DeleteMapping("/meeting/{transferId}")
    public ResponseEntity<Void> deleteMeetingByTransferId(@PathVariable Long transferId) {
        transferService.deleteMeeting(transferId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransferHistoryListResponse>> getTransferHistoryList(@AuthMember MemberInfo memberInfo, @ModelAttribute TransferHistoryListRequest transferHistoryListRequest) {
        return ResponseEntity.ok(transferService.getTransferHistoryList(memberInfo.getId(), transferHistoryListRequest));
    }
}