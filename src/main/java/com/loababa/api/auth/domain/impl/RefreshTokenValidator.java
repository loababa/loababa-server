package com.loababa.api.auth.domain.impl;

import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.domain.impl.repository.RefreshTokenReader;
import com.loababa.api.auth.domain.impl.repository.RefreshTokenWriter;
import com.loababa.api.auth.exception.InvalidTokenException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.loababa.api.auth.exception.AuthClientExceptionInfo.INVALID_TOKEN;

@Component
@RequiredArgsConstructor
public class RefreshTokenValidator {

    public final RefreshTokenReader refreshTokenReader;
    public final RefreshTokenWriter refreshTokenWriter;

    public void validate(RefreshToken refreshToken) {
        verifyExistence(refreshToken);
        refreshTokenWriter.invalidateRefreshToken(refreshToken);
    }

    private void verifyExistence(RefreshToken refreshToken) {
        if (!refreshTokenReader.existRefreshToken(refreshToken)) {
            throw new InvalidTokenException(
                    INVALID_TOKEN,
                    new ServerExceptionInfo("존재하지 않는 RefreshToken 입니다. RefreshToken: " + refreshToken)
            );
        }
    }

}
