package com.loababa.api.auth.domain.impl;


import com.loababa.api.auth.domain.impl.model.AuthToken;

public interface JWTManager {

    AuthToken generate();

}
