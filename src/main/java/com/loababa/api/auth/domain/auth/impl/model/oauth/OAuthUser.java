package com.loababa.api.auth.domain.auth.impl.model.oauth;

public record OAuthUser(
        OAuthPlatform oAuthPlatform,
        Long oAuthId
) {
}
