package com.codesmith.goojangmember.member.persistence;

import com.codesmith.goojangmember.member.persistence.domain.HospitalDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HospitalDetailSupportRepository {

    @Query(value = """
        SELECT id FROM hospital_detail WHERE (
            6371 * ACOS(COS(RADIANS(:latitude))
                * COS(RADIANS(latitude))
                * COS(RADIANS(longitude) - RADIANS(:longitude))
                + SIN(RADIANS(:latitude))
                * SIN(RADIANS(latitude))
            )
        ) < :distance""", nativeQuery = true)
    List<String> findHospitalWithinDistance(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("distance") Double distance);
}
