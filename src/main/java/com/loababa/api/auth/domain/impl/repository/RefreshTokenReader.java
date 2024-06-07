package com.loababa.api.auth.domain.impl.repository;

import com.loababa.api.auth.domain.impl.model.RefreshToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public interface RefreshTokenReader {

    @Transactional(readOnly = true)
    boolean existRefreshToken(RefreshToken refreshToken);

}
