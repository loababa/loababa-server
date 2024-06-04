package com.loababa.api.auth.domain.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.loababa.api.auth.domain.impl.model.AccessToken;
import com.loababa.api.auth.domain.impl.model.AuthToken;
import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.domain.impl.model.JWTProperties;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class Auth0JWTManager implements JWTManager {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final JWTProperties jwtProperties;

    public Auth0JWTManager(
            JWTProperties jwtProperties
    ) {
        this.algorithm = Algorithm.HMAC256(jwtProperties.secretKey());
        this.verifier = JWT.require(algorithm).build();
        this.jwtProperties = jwtProperties;
    }

    @Override
    public AuthToken generate() {
        Instant now = Instant.now();
        AccessToken accessToken = generateAccessToken(now);
        RefreshToken refreshToken = generateRefreshToken(now);
        return new AuthToken(accessToken, refreshToken);
    }

    private AccessToken generateAccessToken(Instant now) {
        return new AccessToken(createToken(now, jwtProperties.accessTokenExpirationTimeInSec()));
    }

    private RefreshToken generateRefreshToken(Instant now) {
        return new RefreshToken(createToken(now, jwtProperties.refreshTokenExpirationTimeInSec()));
    }

    private String createToken(Instant now, int expirationTime) {
        return JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(expirationTime))
                .sign(algorithm);
    }

}

