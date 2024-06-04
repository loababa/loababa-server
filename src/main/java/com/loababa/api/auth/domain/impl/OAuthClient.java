package com.loababa.api.auth.domain.impl;

import com.loababa.api.auth.domain.impl.model.OAuthPlatform;
import com.loababa.api.auth.domain.impl.model.OAuthToken;
import com.loababa.api.auth.domain.impl.model.OAuthUserInfo;

public interface OAuthClient {

    OAuthToken fetchOAuthToken(String code);

    OAuthUserInfo fetchUserOAuthInfo(String accessToken);

    void invalidateOAuthToken(String accessToken);

    OAuthPlatform getOAuthPlatform();

}
