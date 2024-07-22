package com.loababa.api.auth.domain.member.impl.repository;

import com.loababa.api.auth.domain.member.impl.model.LossamBasicInfos;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MemberReader {

    boolean existNickname(String nickname);

    Long getMemberIdByOAuthUserId(Long oAuthUserId);

    LossamBasicInfos findAllLossamInfo();
}
