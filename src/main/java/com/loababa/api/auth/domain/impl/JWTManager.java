package com.loababa.api.auth.domain.impl;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.loababa.api.auth.domain.impl.model.AuthToken;
import com.loababa.api.auth.ui.AuthCredential;

public interface JWTManager {

    AuthToken generate(AuthCredential authCredential);

    AuthCredential extractClaims(String token);

    DecodedJWT verify(String token);

}
