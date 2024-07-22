package com.loababa.api.auth.domain.auth.impl.repository;


import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthUser;
import jakarta.transaction.Transactional;

public interface OAuthUserWriter {

    @Transactional
    Long save(OAuthUser oAuthUser);

}
