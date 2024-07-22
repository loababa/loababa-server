package com.loababa.api.auth.domain.auth.impl.model.token;

import static com.loababa.api.auth.domain.auth.impl.model.token.JWTProperties.TOKEN_PREFIX;

public record AccessToken(
        String value
) {

    public AccessToken(String value) {
        this.value = TOKEN_PREFIX + value;
    }
}
