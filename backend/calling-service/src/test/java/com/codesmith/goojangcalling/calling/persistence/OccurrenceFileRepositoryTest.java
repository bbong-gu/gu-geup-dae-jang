package com.codesmith.goojangcalling.calling.persistence;

import com.codesmith.goojangcalling.calling.dto.request.CallingRequest;
import com.codesmith.goojangcalling.calling.dto.response.FileUploadResponse;
import com.codesmith.goojangcalling.calling.persistence.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OccurrenceFileRepositoryTest {
    @Autowired
    TestEntityManager em;

    private Occurrence occurrence;
    private Long memberId;
    private CallingRequest callingRequest;
    private List<FileUploadResponse> files;

    @BeforeEach
    void setCallingRequest() {
        files = new ArrayList<>();
        files.add(new FileUploadResponse("https://codesmith-ggdj.s3.ap-northeast-2.amazonaws.com/62119bee-726d-4bd5-b6aa-07e65b39b951%EC%9C%A1%EA%B0%9C%EC%9E%A5.png", "image/png",122776L));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "추락"));
        memberId = 521L;
        callingRequest = new CallingRequest(files, KTAS.KTAS2, AgeGroup.YOUTH, Gender.MALE, "아파요", tags, 35.123, 127.123);
        occurrence = new Occurrence(memberId, callingRequest.getKtas(), callingRequest.getAgeGroup(), callingRequest.getGender(), callingRequest.getSymptom(), callingRequest.getLatitude(), callingRequest.getLongitude());
    }

    @DisplayName("사고파일을 저장한다.")
    @Test
    void 사고파일을_저장한다() throws Exception {
        FileUploadResponse fileUploadResponse = files.get(0);
        OccurrenceFile occurrenceFile = new OccurrenceFile(occurrence, fileUploadResponse.getUploadUrl(), fileUploadResponse.getContentType(), fileUploadResponse.getSize());

        OccurrenceFile savedOccurrenceFile = em.persist(occurrenceFile);

        assertThat(savedOccurrenceFile.getOccurrence()).isEqualTo(occurrence);
        assertThat(savedOccurrenceFile.getSavedFileName()).isEqualTo(fileUploadResponse.getUploadUrl());
        assertThat(savedOccurrenceFile.getContentType()).isEqualTo(fileUploadResponse.getContentType());
        assertThat(savedOccurrenceFile.getSize()).isEqualTo(fileUploadResponse.getSize());
    }
}