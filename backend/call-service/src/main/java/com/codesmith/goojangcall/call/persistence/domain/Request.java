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
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private Occurrence occurrence;
    private Long memberId;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime responseTime;
    private String reason;
    @CreatedDate
    private LocalDateTime createdAt;

    public Request(Occurrence occurrence, Long memberId, Status status, LocalDateTime responseTime, String reason) {
        this.occurrence = occurrence;
        this.memberId = memberId;
        this.status = status;
        this.responseTime = responseTime;
        this.reason = reason;
    }
}
