package com.loababa.api.auth.domain.impl.model;

import static com.loababa.api.auth.domain.impl.model.JWTProperties.*;

public record RefreshToken(
        String value
) {
    public RefreshToken(String value) {
        this.value = TOKEN_PREFIX + value;
    }
}
