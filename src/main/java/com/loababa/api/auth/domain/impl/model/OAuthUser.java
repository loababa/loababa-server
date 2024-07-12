package com.loababa.api.auth.domain.impl.model;

public record OAuthUser(
        OAuthPlatform oAuthPlatform,
        Long oAuthId
) {
}
