package com.loababa.api.auth.domain.auth.model.oauth;

import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthUserInfo;
import org.instancio.Instancio;

public class OAuthUserInfoFixtures {

    public static OAuthUserInfo newOAuthUserInfo() {
        return new OAuthUserInfo(Instancio.create(Long.class));
    }

}
