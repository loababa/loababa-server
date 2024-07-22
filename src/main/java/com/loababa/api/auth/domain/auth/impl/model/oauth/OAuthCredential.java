package com.loababa.api.auth.domain.auth.impl.model.oauth;

public record OAuthCredential(
        OAuthPlatform platform,
        String code
) {
}
