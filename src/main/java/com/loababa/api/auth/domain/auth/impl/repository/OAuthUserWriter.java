package com.loababa.api.auth.domain.auth.impl.repository;

import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthUser;

public interface OAuthUserWriter {

    Long save(OAuthUser oAuthUser);

}
