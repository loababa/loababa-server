package com.loababa.api.auth.domain.auth.impl.repository;

import com.loababa.api.auth.domain.auth.impl.model.token.RefreshToken;
import org.springframework.stereotype.Component;

@Component
public interface RefreshTokenReader {

    boolean existRefreshToken(RefreshToken refreshToken);

}
