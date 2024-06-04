package com.loababa.api.auth.infra.client.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoOAuthToken(
        String tokenType,
        String accessToken,
        int expiresIn,
        String refreshToken,
        int refreshTokenExpiresIn,
        String scope
) {

}
