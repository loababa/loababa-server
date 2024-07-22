package com.loababa.api.auth.domain.auth.impl.repository;

import com.loababa.api.auth.domain.auth.impl.model.token.RefreshToken;
import jakarta.transaction.Transactional;

public interface RefreshTokenWriter {

    @Transactional
    void save(RefreshToken refreshToken);

    @Transactional
    void invalidateRefreshToken(RefreshToken refreshToken);

}
