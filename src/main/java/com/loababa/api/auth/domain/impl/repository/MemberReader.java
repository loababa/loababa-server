package com.loababa.api.auth.domain.impl.repository;

import org.springframework.transaction.annotation.Transactional;

public interface MemberReader {

    @Transactional(readOnly = true)
    boolean existNickname(String nickname);

}
