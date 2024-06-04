package com.loababa.api.auth.domain.impl.model;

import org.instancio.Instancio;

public class OAuthUserInfoFixtures {

    public static OAuthUserInfo newOAuthUserInfo() {
        return new OAuthUserInfo(Instancio.create(Long.class));
    }

}
