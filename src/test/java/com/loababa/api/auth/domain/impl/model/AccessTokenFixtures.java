package com.loababa.api.auth.domain.impl.model;

public final class AccessTokenFixtures {

    private AccessTokenFixtures() {
    }

    public static AccessToken newAccessToken() {
        return new AccessToken("accessToken");
    }
}
