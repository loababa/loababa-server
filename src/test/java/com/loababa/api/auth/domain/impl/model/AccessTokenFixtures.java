package com.loababa.api.auth.domain.impl.model;

import com.loababa.api.auth.domain.auth.impl.model.token.AccessToken;

public final class AccessTokenFixtures {

    private AccessTokenFixtures() {
    }

    public static AccessToken newAccessToken() {
        return new AccessToken("accessToken");
    }
}
