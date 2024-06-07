package com.loababa.api.auth.domain.impl;

import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.domain.impl.repository.RefreshTokenReader;
import com.loababa.api.auth.exception.InvalidTokenException;
import com.loababa.api.common.exception.ServerErrorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenValidator {

    public final RefreshTokenReader refreshTokenReader;

    public void validate(RefreshToken refreshToken) {
        if (!refreshTokenReader.existRefreshToken(refreshToken)) {
            throw new InvalidTokenException(
                    "RefreshToken 갱신에 실패했습니다.",
                    new ServerErrorInfo(null, "존재하지 않는 RefreshToken 입니다. RefreshToken: " + refreshToken)
            );
        }
    }

}
