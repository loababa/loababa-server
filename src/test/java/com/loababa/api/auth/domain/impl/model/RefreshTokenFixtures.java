package com.loababa.api.auth.domain.impl.model;

public final class RefreshTokenFixtures {

    private RefreshTokenFixtures() {
    }

    public static RefreshToken newRefreshToken() {
        return new RefreshToken("refreshToken");
    }
}
