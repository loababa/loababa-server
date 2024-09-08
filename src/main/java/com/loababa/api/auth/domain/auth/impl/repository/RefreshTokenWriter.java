package com.loababa.api.auth.domain.auth.impl.repository;

import com.loababa.api.auth.domain.auth.impl.model.token.RefreshToken;

public interface RefreshTokenWriter {

    void save(RefreshToken refreshToken);

    void invalidateRefreshToken(RefreshToken refreshToken);

}
