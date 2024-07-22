package com.loababa.api.auth.domain.auth.model.oauth;

import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthToken;

public class OAuthTokenFixtures {

    public static OAuthToken newOAuthToken() {
        return new OAuthToken("accessToken");
    }

}
