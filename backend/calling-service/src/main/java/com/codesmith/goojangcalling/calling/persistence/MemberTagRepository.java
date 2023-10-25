package com.codesmith.goojangcalling.calling.persistence;

import com.codesmith.goojangcalling.calling.dto.response.MemberTagResponse;
import com.codesmith.goojangcalling.calling.persistence.domain.MemberTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberTagRepository extends JpaRepository<MemberTag, Long> {

    @Query("select new com.codesmith.goojangcalling.calling.dto.response.MemberTagResponse(m.tag) " +
            "from MemberTag m where m.memberId = :memberId")
    List<MemberTagResponse> findByMemberId (@Param("memberId") Long memberId);
}
