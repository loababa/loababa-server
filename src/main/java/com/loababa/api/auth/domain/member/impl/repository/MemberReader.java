package com.loababa.api.auth.domain.member.impl.repository;

import com.loababa.api.auth.domain.member.impl.model.LossamBasicInfos;
import com.loababa.api.auth.domain.member.impl.model.MemberType;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MemberReader {

    boolean existNickname(String nickname);

    Long getMemberIdByOAuthUserId(Long oAuthUserId);

    MemberType readMemberType(Long memberId);

    LossamBasicInfos findAllLossamInfo();
}
