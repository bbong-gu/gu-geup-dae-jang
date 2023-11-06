package com.codesmith.goojangtransfer.transfer.presentation;

import com.codesmith.goojangtransfer.transfer.application.TransferService;
import com.codesmith.goojangtransfer.transfer.dto.request.TransferCreateRequest;
import com.codesmith.goojangtransfer.transfer.dto.response.TransferCreateResponse;
import com.codesmith.goojangtransfer.transfer.dto.response.TransferListResponse;
import com.codesmith.goojangtransfer.transfer.dto.response.TransferStatusChangeResponse;
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
}