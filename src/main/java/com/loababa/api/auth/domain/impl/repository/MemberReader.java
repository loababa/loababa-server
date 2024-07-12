package com.loababa.api.auth.domain.impl.repository;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MemberReader {

    boolean existNickname(String nickname);

    Long getMemberIdByOAuthUserId(Long oAuthUserId);

}
