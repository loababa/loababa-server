package com.loababa.api.auth.domain.impl.model;

public record AuthToken(
        AccessToken accessToken,
        RefreshToken refreshToken
) {
}
