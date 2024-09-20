package com.loababa.api.auth.infra.persistance.adapter.member;

import com.loababa.api.auth.domain.member.impl.model.LossamBasicInfos;
import com.loababa.api.auth.domain.member.impl.model.Member;
import com.loababa.api.auth.domain.member.impl.model.MemberProfile;
import com.loababa.api.auth.domain.member.impl.model.MemberType;
import com.loababa.api.auth.domain.member.impl.repository.MemberReader;
import com.loababa.api.auth.domain.member.impl.repository.MemberWriter;
import com.loababa.api.auth.infra.persistance.dto.LossamBasicInfoDto;
import com.loababa.api.auth.infra.persistance.entity.MemberEntity;
import com.loababa.api.auth.infra.persistance.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberJpaRepositoryAdapter implements MemberReader, MemberWriter {

    private final MemberJpaRepository memberJpaRepository;

    @Transactional(readOnly = true)
    @Override
    public boolean existNickname(String nickname) {
        return memberJpaRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getMemberIdByOAuthUserId(Long oAuthUserId) {
        return memberJpaRepository.findIdByOAuthUserId(oAuthUserId);
    }

    @Transactional(readOnly = true)
    @Override
    public MemberType readMemberType(Long memberId) {
        MemberEntity memberEntity = memberJpaRepository.getMemberEntityById(memberId);
        return memberEntity.getMemberType();
    }

    @Transactional(readOnly = true)
    @Override
    public LossamBasicInfos getAllLossamInfo() {
        List<LossamBasicInfoDto> allLossamInfo = memberJpaRepository.findAllLossamInfo();
        return new LossamBasicInfos(
                allLossamInfo.stream()
                        .map(LossamBasicInfoDto::toLossamBasicInfo)
                        .toList()
        );
    }

    @Override
    public Member read(Long memberId) {
        MemberEntity entity = memberJpaRepository.getMemberEntityById(memberId);
        return entity.toMember();
    }

    @Transactional
    @Override
    public Long save(MemberProfile memberProfile, Long oauthId) {
        var memberEntity = MemberEntity.builder()
                .nickname(memberProfile.nickname())
                .memberType(memberProfile.memberType())
                .profileImageUrl(memberProfile.profileImageUrl())
                .oAuthUserId(oauthId)
                .build();
        return memberJpaRepository.save(memberEntity)
                .getId();
    }
}
