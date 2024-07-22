package com.loababa.api.auth.domain.auth;

import com.loababa.api.auth.domain.auth.impl.model.token.JWTManager;
import com.loababa.api.auth.domain.auth.impl.model.token.RefreshTokenValidator;
import com.loababa.api.auth.domain.auth.impl.model.token.AuthToken;
import com.loababa.api.auth.domain.auth.impl.model.token.RefreshToken;
import com.loababa.api.auth.domain.auth.impl.repository.RefreshTokenWriter;
import com.loababa.api.common.model.AuthCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenWriter refreshTokenWriter;
    private final RefreshTokenValidator refreshTokenValidator;
    private final JWTManager jwtManager;

    public AuthToken refreshAuthToken(RefreshToken refreshToken) {
        refreshTokenValidator.validate(refreshToken);
        AuthCredential authCredential = jwtManager.extractClaims(refreshToken.value());
        return jwtManager.generate(authCredential);
    }

}
