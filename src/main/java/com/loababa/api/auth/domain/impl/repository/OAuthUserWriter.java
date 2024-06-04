package com.loababa.api.auth.domain.impl.repository;


import com.loababa.api.auth.domain.impl.model.OAuthUser;
import jakarta.transaction.Transactional;

public interface OAuthUserWriter {

    @Transactional
    void save(OAuthUser oAuthUser);

}
