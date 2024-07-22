package com.loababa.api.auth.ui.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthTokenRefreshReq(
        @NotBlank String refreshToken
) {
}
