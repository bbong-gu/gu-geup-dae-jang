package com.codesmith.goojangcalling.calling.dto.request;

import com.codesmith.goojangcalling.calling.dto.response.FileUploadResponse;
import com.codesmith.goojangcalling.calling.persistence.domain.AgeGroup;
import com.codesmith.goojangcalling.calling.persistence.domain.Gender;
import com.codesmith.goojangcalling.calling.persistence.domain.KTAS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CallingRequest {
    private List<FileUploadResponse> files;
    private KTAS ktas;
    private AgeGroup ageGroup;
    private Gender gender;
    private String symptom;
    private List<String> tagNames;
    private Double latitude;
    private Double longitude;
}