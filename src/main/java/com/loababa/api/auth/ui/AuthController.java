package com.loababa.api.auth.ui;

import com.loababa.api.auth.domain.auth.AuthService;
import com.loababa.api.auth.domain.auth.OAuthService;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthCredential;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthPlatform;
import com.loababa.api.auth.domain.auth.impl.model.token.AuthToken;
import com.loababa.api.auth.domain.auth.impl.model.token.RefreshToken;
import com.loababa.api.auth.ui.dto.AuthTokenRefreshReq;
import com.loababa.api.common.model.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증/인가 관련 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final OAuthService oAuthService;

    @Operation(description = "소셜 로그인")
    @GetMapping("/api/v1/oauth/{platform}")
    public ApiResponse<AuthToken> requestAuthentication(
            @Schema(description = "OAuth 플랫폼", allowableValues = {"kakao"})
            @PathVariable
            @Valid @NotBlank @Pattern(regexp = "^(kakao)$") String platform,
            @Schema(description = "OAuth 토큰을 받기 위한 인가 코드")
            @RequestParam
            @Valid @NotBlank String code
    ) {
        OAuthPlatform oAuthPlatform = OAuthPlatform.from(platform);
        var authToken = oAuthService.authenticate(new OAuthCredential(oAuthPlatform, code));
        return ApiResponse.success(authToken);
    }

    @Operation(description = "토큰 재발급")
    @PostMapping("/api/v1/auth/refresh")
    public ApiResponse<AuthToken> requestTokenRefresh(
            @RequestBody @Valid AuthTokenRefreshReq authTokenRefreshReq
    ) {
        RefreshToken refreshToken = new RefreshToken(authTokenRefreshReq.refreshToken());
        AuthToken authToken = authService.refreshAuthToken(refreshToken);
        return ApiResponse.success(authToken);
    }

}
