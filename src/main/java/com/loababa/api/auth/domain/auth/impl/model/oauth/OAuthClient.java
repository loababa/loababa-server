package com.loababa.api.auth.domain.auth.impl.model.oauth;

public interface OAuthClient {

    OAuthToken fetchOAuthToken(String code);

    OAuthUserInfo fetchUserOAuthInfo(String accessToken);

    void invalidateOAuthToken(String accessToken);

    OAuthPlatform getOAuthPlatform();

}
