package com.loababa.api.auth.domain.impl.repository;

import com.loababa.api.auth.domain.impl.model.LossamBasicInfos;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MemberReader {

    boolean existNickname(String nickname);

    Long getMemberIdByOAuthUserId(Long oAuthUserId);

    LossamBasicInfos findAllLossamInfo();
}
