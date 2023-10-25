package com.codesmith.goojangcalling.calling.application;

import com.codesmith.goojangcalling.calling.dto.response.MemberTagResponse;
import com.codesmith.goojangcalling.calling.persistence.MemberTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberTagServiceImpl implements MemberTagService{

    private final MemberTagRepository memberTagRepository;

    @Override
    public List<MemberTagResponse> getMemberTagList(Long memberId) {
        return memberTagRepository.findByMemberId(memberId);
    }
}