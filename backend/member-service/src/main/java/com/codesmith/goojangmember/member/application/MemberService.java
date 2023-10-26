package com.codesmith.goojangmember.member.application;

import com.codesmith.goojangmember.member.persistence.domain.Member;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

public interface MemberService {
    Member getMemberInfo(Long memberId);
    List<String> getHospitalListFromHere(Double latitude, Double longitude, Double distance);
}
