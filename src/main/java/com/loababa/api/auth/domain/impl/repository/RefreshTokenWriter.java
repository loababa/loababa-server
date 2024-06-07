package com.loababa.api.auth.domain.impl.repository;

import com.loababa.api.auth.domain.impl.model.RefreshToken;
import jakarta.transaction.Transactional;

public interface RefreshTokenWriter {

    @Transactional
    void save(RefreshToken refreshToken);

    @Transactional
    void invalidateRefreshToken(RefreshToken refreshToken);

}
