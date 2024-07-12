package com.loababa.api.auth.domain;

import com.loababa.api.auth.domain.impl.JWTManager;
import com.loababa.api.auth.domain.impl.RefreshTokenValidator;
import com.loababa.api.auth.domain.impl.model.AuthToken;
import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.domain.impl.repository.RefreshTokenWriter;
import com.loababa.api.auth.ui.AuthCredential;
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
