package com.loababa.api.auth.infra.persistance.adapter.member;

import com.loababa.api.auth.domain.impl.model.LossamBasicInfos;
import com.loababa.api.auth.domain.impl.model.MemberProfile;
import com.loababa.api.auth.domain.impl.repository.MemberReader;
import com.loababa.api.auth.domain.impl.repository.MemberWriter;
import com.loababa.api.auth.infra.persistance.dto.LossamBasicInfoDto;
import com.loababa.api.auth.infra.persistance.entity.MemberEntity;
import com.loababa.api.auth.infra.persistance.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public LossamBasicInfos findAllLossamInfo() {
        List<LossamBasicInfoDto> allLossamInfo = memberJpaRepository.findAllLossamInfo();
        return new LossamBasicInfos(
                allLossamInfo.stream()
                        .map(LossamBasicInfoDto::toLossamBasicInfo)
                        .toList()
        );
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
