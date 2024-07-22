package com.loababa.api.auth.domain.auth.impl.model.token;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.loababa.api.common.model.AuthCredential;

public interface JWTManager {

    AuthToken generate(AuthCredential authCredential);

    AuthCredential extractClaims(String token);

    DecodedJWT verify(String token);

}
