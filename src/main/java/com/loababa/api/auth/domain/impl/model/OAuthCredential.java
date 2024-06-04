package com.loababa.api.auth.domain.impl.model;

public record OAuthCredential(
        OAuthPlatform platform,
        String code
) {
}
