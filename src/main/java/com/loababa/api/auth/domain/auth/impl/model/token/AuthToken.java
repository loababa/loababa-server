package com.loababa.api.auth.domain.auth.impl.model.token;

public record AuthToken(
        AccessToken accessToken,
        RefreshToken refreshToken
) {
}
