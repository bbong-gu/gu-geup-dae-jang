package com.codesmith.goojangcalling.calling.application;

import com.codesmith.goojangcalling.calling.dto.request.CallingStatusChangeRequest;
import com.codesmith.goojangcalling.calling.dto.request.CallingCreateRequest;
import com.codesmith.goojangcalling.calling.dto.request.OccurrenceCreateRequest;
import com.codesmith.goojangcalling.calling.dto.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CallingService {
    OccurrenceCreateResponse addOccurrence (Long memberId, OccurrenceCreateRequest occurrenceCreateRequest);

    List<HospitalSearchResponse> searchHospital(Double latitude, Double longitude, Double distance);

    List<CallingStatusResponse> createCalling(Long memberId, CallingCreateRequest callingCreateRequest);

    void createCallingMessage(List<CallingStatusResponse> callingStatusResponseList, Long occurrenceId);

    List<FileUploadResponse> fileUpload(List<MultipartFile> files);

    HospitalStatusResponse changeCallingStatus(CallingStatusChangeRequest changeMessage);

    TransferCreateResponse createTransfer(Long callingId);

    void cancelCallingStatus(Long callingId);
}