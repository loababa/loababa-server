package com.loababa.api.auth.domain.auth.model.token;

import com.loababa.api.auth.domain.auth.impl.model.token.AuthToken;
import org.instancio.Instancio;

import static org.instancio.Select.field;

public final class AuthTokenFixtures {
    private AuthTokenFixtures() {
    }

    public static AuthToken newAuthToken() {
        return Instancio.of(AuthToken.class)
                .set(field(AuthToken::accessToken), AccessTokenFixtures.newAccessToken())
                .set(field(AuthToken::refreshToken), RefreshTokenFixtures.newRefreshToken())
                .create();
    }
}
