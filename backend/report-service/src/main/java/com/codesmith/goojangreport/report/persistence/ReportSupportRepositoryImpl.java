package com.codesmith.goojangreport.report.persistence;

import com.codesmith.goojangreport.report.persistence.domain.ReportHeader;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.codesmith.goojangreport.report.persistence.domain.QReport.report;

@Repository
public class ReportSupportRepositoryImpl implements ReportSupportRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public ReportSupportRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public ReportHeader getHeaderValue(Long memberId) {
        return queryFactory
                .select(Projections.constructor(ReportHeader.class,
                        getToday(memberId),
                        getTodayApproved(memberId),
                        getTodayRejected(memberId),
                        getAvgResponseTime(memberId),
                        getAvgTransferTime(memberId)))
                .from(report)
                .fetchFirst();
    }

    public JPQLQuery<Long> getToday(Long memberId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.plusDays(1).atStartOfDay();

        return JPAExpressions
                .select(report.id.count())
                .from(report)
                .where(
                        report.hospitalMemberId.eq(memberId)
                                .and(report.occurrenceTime.between(startOfToday, endOfToday))
                );
    }

    public JPQLQuery<Long> getTodayApproved(Long memberId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.plusDays(1).atStartOfDay();

        return JPAExpressions
                .select(report.id.count())
                .from(report)
                .where(
                        report.hospitalMemberId.eq(memberId)
                                .and(report.occurrenceTime.between(startOfToday, endOfToday))
                                .and(report.callingStatus.eq("APPROVED"))
                );
    }

    public JPQLQuery<Long> getTodayRejected(Long memberId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.plusDays(1).atStartOfDay();

        return JPAExpressions
                .select(report.id.count())
                .from(report)
                .where(
                        report.hospitalMemberId.eq(memberId)
                                .and(report.occurrenceTime.between(startOfToday, endOfToday))
                                .and(report.callingStatus.eq("REJECTED"))
                );
    }

    public JPQLQuery<Double> getAvgResponseTime(Long memberId) {
        return JPAExpressions
                .select(report.id.avg())
                .from(report)
                .where(report.id.eq(1L));
    }

    public JPQLQuery<Double> getAvgTransferTime(Long memberId) {
        return JPAExpressions
                .select(report.id.avg())
                .from(report)
                .where(report.id.eq(1L));
    }
}
