package com.loababa.api.auth.domain.auth.impl.repository;

import com.loababa.api.auth.domain.auth.impl.model.token.RefreshToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public interface RefreshTokenReader {

    @Transactional(readOnly = true)
    boolean existRefreshToken(RefreshToken refreshToken);

}
