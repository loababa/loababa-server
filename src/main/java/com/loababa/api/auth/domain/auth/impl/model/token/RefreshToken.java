package com.loababa.api.auth.domain.auth.impl.model.token;

public record RefreshToken(
        String value
) {
    public RefreshToken(String value) {
        this.value = JWTProperties.TOKEN_PREFIX + value;
    }
}
