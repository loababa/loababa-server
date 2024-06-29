package com.loababa.api.auth.infra.persistance.adapter;

import com.loababa.api.auth.infra.persistance.adapter.member.MemberJpaRepositoryAdapter;
import com.loababa.api.common.MockTestBase;
import com.loababa.api.auth.infra.persistance.repository.MemberJpaRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;


class MemberJpaRepositoryAdapterTest extends MockTestBase {

    @InjectMocks
    private MemberJpaRepositoryAdapter memberJpaRepositoryAdapter;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Nested
    class 중복되는_닉네임이_존재하는지_확인할_수_있다 {

        @Test
        void 중복되지_않는_닉네임() {
            // given
            String nickname = "중복되지 않는 닉네임";
            given(memberJpaRepository.existsByNickname(nickname)).willReturn(false);

            // when
            boolean isDuplicatedNickname = memberJpaRepositoryAdapter.existNickname(nickname);

            // then
            assertThat(isDuplicatedNickname).isFalse();
        }

        @Test
        void 중복되는_닉네임() {
            // given
            String nickname = "중복되는_닉네임";
            given(memberJpaRepository.existsByNickname(nickname)).willReturn(true);

            // when
            boolean isDuplicatedNickname = memberJpaRepositoryAdapter.existNickname(nickname);

            // then
            assertThat(isDuplicatedNickname).isTrue();
        }

    }

}
