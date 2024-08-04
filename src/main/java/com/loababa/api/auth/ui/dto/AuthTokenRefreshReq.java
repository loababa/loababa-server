package com.loababa.api.auth.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "토큰 재발급 요청 Body")
public record AuthTokenRefreshReq(
        @NotBlank String refreshToken
) {
}
