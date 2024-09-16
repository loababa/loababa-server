package com.loababa.api.auth.domain.auth.impl.model.oauth;

public interface OAuthClient {

    OAuthToken fetchOAuthToken(String code, String redirectUri);

    OAuthUserInfo fetchUserOAuthInfo(String accessToken);

    void invalidateOAuthToken(String accessToken);

    OAuthPlatform getOAuthPlatform();

}
