package com.loababa.api.auth.domain.impl.model;

import static com.loababa.api.auth.domain.impl.model.JWTProperties.TOKEN_PREFIX;

public record AccessToken(
        String value
) {

    public AccessToken(String value) {
        this.value = TOKEN_PREFIX + value;
    }
}
