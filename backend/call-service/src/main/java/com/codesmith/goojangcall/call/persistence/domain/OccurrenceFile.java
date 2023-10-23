package com.codesmith.goojangcall.call.persistence.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OccurrenceFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Occurrence occurrence;
    private String originalFileName;
    private String savedFileName;
    private String contentType;
    private Long size;
    @CreatedDate
    private LocalDateTime createdAt;

    public OccurrenceFile(Occurrence occurrence, String originalFileName, String savedFileName, String contentType, Long size) {
        this.occurrence = occurrence;
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
        this.contentType = contentType;
        this.size = size;
    }
}
