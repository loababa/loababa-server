package com.loababa.api.auth.ui.dto;

import org.instancio.Instancio;

import static org.instancio.Select.field;

public final class AuthTokenRefreshRequestFixtures {
    private AuthTokenRefreshRequestFixtures() {
    }

    public static AuthTokenRefreshRequest newAuthTokenRefreshRequest() {
        return Instancio.of(AuthTokenRefreshRequest.class)
                .generate(field(AuthTokenRefreshRequest::refreshToken), gen -> gen.string().prefix("Bearer "))
                .create();
    }

}
