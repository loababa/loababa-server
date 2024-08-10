package com.loababa.api.auth.domain.auth.impl.model.token;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.loababa.api.common.model.MemberCredential;

public interface JWTManager {

    AuthToken generate(MemberCredential memberCredential);

    MemberCredential extractClaims(String token);

    DecodedJWT verify(String token);

}
