package com.loababa.api.auth.ui.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthTokenRefreshRequest(
        @NotBlank String refreshToken
) {
}
