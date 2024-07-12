package com.loababa.api.auth.infra.persistance.adapter.member;

import com.loababa.api.auth.domain.impl.model.MemberProfile;
import com.loababa.api.auth.domain.impl.repository.MemberReader;
import com.loababa.api.auth.domain.impl.repository.MemberWriter;
import com.loababa.api.auth.infra.persistance.entity.MemberEntity;
import com.loababa.api.auth.infra.persistance.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberJpaRepositoryAdapter implements MemberReader, MemberWriter {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public boolean existNickname(String nickname) {
        return memberJpaRepository.existsByNickname(nickname);
    }

    @Override
    public Long getMemberIdByOAuthUserId(Long oAuthUserId) {
        return memberJpaRepository.findIdByOAuthUserId(oAuthUserId);
    }

    @Override
    public Long save(MemberProfile memberProfile, Long oauthId) {
        var memberEntity = MemberEntity.builder()
                .nickname(memberProfile.nickname())
                .memberType(memberProfile.memberType())
                .profileImageUrl(
                        memberProfile.profileImageURL()
                                .value()
                )
                .oAuthUserId(oauthId)
                .build();
        return memberJpaRepository.save(memberEntity)
                .getId();
    }
}
