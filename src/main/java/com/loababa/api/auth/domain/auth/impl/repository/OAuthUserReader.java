package com.loababa.api.auth.domain.auth.impl.repository;

import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthUser;

public interface OAuthUserReader {

    boolean isRegisteredAuthUser(OAuthUser oAuthUser);

    Long getId(OAuthUser oAuthUser);

}
