package com.loababa.api.auth.domain.auth.model.oauth;

import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthPlatform;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthToken;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthUserInfo;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthClient;

import static org.mockito.Mockito.mock;

public class OAuthClientFixtures {

    public static OAuthClient mockOAuthClient() {
        return mock();
    }

    public static OAuthClient fakeOAuthClient(OAuthPlatform platform) {
        return new OAuthClient() {
            @Override
            public OAuthToken fetchOAuthToken(String code, String redirectUri) {
                return OAuthTokenFixtures.newOAuthToken();
            }

            @Override
            public OAuthUserInfo fetchUserOAuthInfo(String accessToken) {
                return OAuthUserInfoFixtures.newOAuthUserInfo();
            }

            @Override
            public void invalidateOAuthToken(String accessToken) {
                // OAuth Token 무효화
            }

            @Override
            public OAuthPlatform getOAuthPlatform() {
                return platform;
            }
        };
    }

}
