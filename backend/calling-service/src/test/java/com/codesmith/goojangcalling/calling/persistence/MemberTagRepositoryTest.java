package com.codesmith.goojangcalling.calling.persistence;

import com.codesmith.goojangcalling.calling.persistence.domain.MemberTag;
import com.codesmith.goojangcalling.calling.persistence.domain.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberTagRepositoryTest {

    @Autowired
    private MemberTagRepository memberTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @DisplayName("사용자 태그를 조회한다.")
    @Test
    void 사용자_태그를_조회한다() throws Exception {
        Long memberId = 521L;

        List<Tag> tagList = memberTagRepository.findByMemberId(memberId);

        assertThat(tagList.size()).isGreaterThan(0);

    }

    @DisplayName("추가할 태그가 존재하면 사용자태그에 추가한다.")
    @Test
    void 추가할_태그가_존재하면_사용자태그에_추가한다() throws Exception {
        String inputTagName = "추락";
        Long memberId = 521L;

        Tag tag = tagRepository.findByName(inputTagName).orElseThrow();
        MemberTag memberTag = new MemberTag(memberId, tag);
        MemberTag savedMemberTag = memberTagRepository.save(memberTag);

        assertThat(savedMemberTag.getTag()).isEqualTo(tag);
        assertThat(savedMemberTag.getMemberId()).isEqualTo(521L);
    }

    @DisplayName("추가할 태그가 존재하지 않으면 태그를 생성하고 사용자태그에 추가한다.")
    @Test
    void 추가할_태그가_존재하지_않으면_태그를_생성하고_사용자태그에_추가한다() throws Exception {
        //given
        String inputTagName = "추락1";

        //when
        assertThrows(NoSuchElementException.class, () -> {
            tagRepository.findByName(inputTagName).orElseThrow();
        });

        //then

    }
}