package com.loababa.api.auth.ui;

import com.loababa.api.auth.domain.auth.AuthService;
import com.loababa.api.auth.domain.auth.OAuthService;
import com.loababa.api.auth.domain.auth.impl.model.token.AccessToken;
import com.loababa.api.auth.domain.auth.model.token.AccessTokenFixtures;
import com.loababa.api.auth.domain.auth.impl.model.token.AuthToken;
import com.loababa.api.auth.domain.auth.model.token.AuthTokenFixtures;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthCredential;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthPlatform;
import com.loababa.api.auth.domain.auth.impl.model.token.RefreshToken;
import com.loababa.api.auth.domain.auth.model.token.RefreshTokenFixtures;
import com.loababa.api.auth.ui.dto.AuthTokenRefreshReq;
import com.loababa.api.auth.ui.dto.AuthTokenRefreshRequestFixtures;
import com.loababa.api.common.ControllerTestBase;
import com.loababa.api.common.model.ApiResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest extends ControllerTestBase {

    @MockBean
    private OAuthService oAuthService;

    @MockBean
    private AuthService authService;

    @Nested
    class OAuth_플랫폼_유효성_검사를_할_수_있다 {

        @ParameterizedTest
        @ValueSource(strings = {"kakao", "google"})
        void 정상요청(String platform) throws Exception {
            // given
            String code = "OAuth 토큰 인가 코드";
            AccessToken accessToken = AccessTokenFixtures.newAccessToken();
            RefreshToken refreshToken = RefreshTokenFixtures.newRefreshToken();
            AuthToken authToken = new AuthToken(accessToken, refreshToken);

            OAuthPlatform oAuthPlatform = OAuthPlatform.from(platform);
            given(oAuthService.authenticate(
                    new OAuthCredential(oAuthPlatform, code)
            )).willReturn(authToken);

            // when
            ResultActions resultActions = mvc.perform(
                    get("/api/v1/oauth/{platform}", platform)
                            .queryParam("code", code)
            );

            // then
            String expectedResponseBody = om.writeValueAsString(
                    ApiResponse.success(authToken)
            );

            resultActions.andExpect(
                            status().isOk()
                    )
                    .andExpect(
                            content().json(expectedResponseBody)
                    );
        }

        @Test
        void 지원하지_않은_플랫폼을_입력했을_때() throws Exception {
            // given
            String code = "OAuth 토큰 인가 코드";
            String platform = "notSupportedPlatform";

            // when
            ResultActions resultActions = mvc.perform(
                    get("/api/v1/oauth/{platform}", platform)
                            .queryParam("code", code)
            );

            // then
            resultActions.andExpect(status().is4xxClientError());
        }

    }

    @Test
    void 토큰_재발급_요청을_할_수_있다() throws Exception {
        // given
        AuthTokenRefreshReq authTokenRefreshReq = AuthTokenRefreshRequestFixtures.newAuthTokenRefreshRequest();

        AuthToken authToken = AuthTokenFixtures.newAuthToken();
        given(authService.refreshAuthToken(new RefreshToken(authTokenRefreshReq.refreshToken())))
                .willReturn(authToken);

        // when
        ResultActions resultActions = mvc.perform(post("/api/v1/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(authTokenRefreshReq)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(ApiResponse.success(authToken))));
    }

}
