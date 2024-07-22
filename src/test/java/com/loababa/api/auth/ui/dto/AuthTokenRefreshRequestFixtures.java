package com.loababa.api.auth.ui.dto;

import org.instancio.Instancio;

import static org.instancio.Select.field;

public final class AuthTokenRefreshRequestFixtures {
    private AuthTokenRefreshRequestFixtures() {
    }

    public static AuthTokenRefreshReq newAuthTokenRefreshRequest() {
        return Instancio.of(AuthTokenRefreshReq.class)
                .generate(field(AuthTokenRefreshReq::refreshToken), gen -> gen.string().prefix("Bearer "))
                .create();
    }

}
