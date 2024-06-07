package com.loababa.api.auth.domain.impl.repository;


import com.loababa.api.auth.domain.impl.model.OAuthUser;

public interface OAuthUserReader {

    boolean isRegisteredAuthUser(OAuthUser oAuthUser);

}
