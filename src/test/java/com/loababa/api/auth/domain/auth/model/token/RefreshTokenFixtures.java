package com.loababa.api.auth.domain.auth.model.token;

import com.loababa.api.auth.domain.auth.impl.model.token.RefreshToken;

public final class RefreshTokenFixtures {

    private RefreshTokenFixtures() {
    }

    public static RefreshToken newRefreshToken() {
        return new RefreshToken("refreshToken");
    }
}
