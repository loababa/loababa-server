package com.loababa.api.auth.ui;

import com.loababa.api.auth.domain.AuthService;
import com.loababa.api.auth.domain.impl.model.AuthToken;
import com.loababa.api.auth.domain.impl.model.AuthTokenFixtures;
import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.ui.dto.AuthTokenRefreshRequest;
import com.loababa.api.auth.ui.dto.AuthTokenRefreshRequestFixtures;
import com.loababa.api.common.ControllerTestBase;
import com.loababa.api.common.model.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest extends ControllerTestBase {

    @MockBean
    private AuthService authService;

    @Test
    void 토큰_재발급_요청을_할_수_있다() throws Exception {
        // given
        AuthTokenRefreshRequest authTokenRefreshRequest = AuthTokenRefreshRequestFixtures.newAuthTokenRefreshRequest();

        AuthToken authToken = AuthTokenFixtures.newAuthToken();
        given(authService.refreshAuthToken(new RefreshToken(authTokenRefreshRequest.refreshToken())))
                .willReturn(authToken);

        // when
        ResultActions resultActions = mvc.perform(post("/api/v1/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(authTokenRefreshRequest)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(ApiResponse.success(authToken))));
    }

}
