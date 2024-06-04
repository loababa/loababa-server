package com.loababa.api.auth.domain.impl.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
public record JWTProperties(
        String secretKey,
        int accessTokenExpirationTimeInSec,
        int refreshTokenExpirationTimeInSec
) {
}
