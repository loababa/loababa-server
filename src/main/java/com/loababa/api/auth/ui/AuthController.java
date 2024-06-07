package com.loababa.api.auth.ui;

import com.loababa.api.auth.domain.AuthService;
import com.loababa.api.auth.domain.impl.model.AuthToken;
import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.ui.dto.AuthTokenRefreshRequest;
import com.loababa.api.common.model.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증/인가 관련 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/v1/auth/refresh")
    public ApiResponse<AuthToken> requestTokenRefresh(
            @Schema(description = "토큰 재발급 요청 Body")
            @RequestBody @Valid AuthTokenRefreshRequest authTokenRefreshRequest
    ) {
        RefreshToken refreshToken = new RefreshToken(authTokenRefreshRequest.refreshToken());
        AuthToken authToken = authService.refreshAuthToken(refreshToken);
        return ApiResponse.success(authToken);
    }

}
