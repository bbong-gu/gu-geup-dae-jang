package com.codesmith.goojangtransfer.transfer.dto.response;

import com.codesmith.goojangtransfer.transfer.persistence.domain.Status;
import com.codesmith.goojangtransfer.transfer.persistence.domain.Transfer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferHistoryResponse {
    private Long transferId;
    private String date;
    private String transferStartTime;
    private String transferEndTime;
    private String hospitalName;
    private String paramedicName;
    private String accidentAddress;
    private String ageGroup;
    private String gender;
    private String ktas;
    private List<String> tags;
    private List<String> files;
    private String description;
    private boolean isCompleted;

    public TransferHistoryResponse(OccurrenceInfoResponse occurrenceInfo, Transfer transfer, String hospitalName) {
        this.transferId = transfer.getId();
        this.date = transfer.getArrivedAt().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        this.transferStartTime = occurrenceInfo.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.transferEndTime = transfer.getArrivedAt().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.hospitalName = hospitalName;
        this.paramedicName = occurrenceInfo.getMemberName();
        this.accidentAddress = occurrenceInfo.getAddress();
        this.ageGroup = occurrenceInfo.getAgeGroup();
        this.gender = occurrenceInfo.getGender();
        this.ktas = occurrenceInfo.getKtas();
        this.tags = occurrenceInfo.getTags();
        this.files = occurrenceInfo.getFiles();
        this.description = occurrenceInfo.getDescription();
        this.isCompleted = transfer.getStatus() == Status.COMPLETE;
    }
}
