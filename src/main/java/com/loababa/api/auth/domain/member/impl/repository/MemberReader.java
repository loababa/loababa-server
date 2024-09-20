package com.loababa.api.auth.domain.member.impl.repository;

import com.loababa.api.auth.domain.member.impl.model.LossamBasicInfos;
import com.loababa.api.auth.domain.member.impl.model.Member;
import com.loababa.api.auth.domain.member.impl.model.MemberType;

public interface MemberReader {

    boolean existNickname(String nickname);

    Long getMemberIdByOAuthUserId(Long oAuthUserId);

    MemberType readMemberType(Long memberId);

    LossamBasicInfos getAllLossamInfo();

    Member read(Long memberId);
}
