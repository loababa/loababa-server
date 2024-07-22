package com.loababa.api.auth.domain.auth.impl.model.token;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
public record JWTProperties(
        String secretKey,
        int accessTokenExpirationTimeInSec,
        int refreshTokenExpirationTimeInSec
) {
    public static final String TOKEN_PREFIX = "Bearer ";
}
