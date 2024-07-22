package com.loababa.api.auth.domain.auth;

import com.loababa.api.auth.domain.auth.impl.model.token.JWTManager;
import com.loababa.api.auth.domain.auth.impl.model.token.RefreshTokenValidator;
import com.loababa.api.auth.domain.auth.impl.model.token.AuthToken;
import com.loababa.api.auth.domain.auth.model.token.AuthTokenFixtures;
import com.loababa.api.auth.domain.auth.model.token.RefreshTokenFixtures;
import com.loababa.api.auth.domain.auth.impl.repository.RefreshTokenWriter;
import com.loababa.api.common.model.AuthCredential;
import com.loababa.api.common.MockTestBase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

class AuthServiceTest extends MockTestBase {

    @InjectMocks
    private AuthService authService;
    @Mock
    private RefreshTokenValidator refreshTokenValidator;
    @Mock
    private RefreshTokenWriter refreshTokenWriter;
    @Mock
    private JWTManager jwtManager;

    @Test
    void 토큰을_재발급할_수_있다() {
        // given
        var refreshToken = RefreshTokenFixtures.newRefreshToken();

        var authCredential = new AuthCredential(1L, 1L);
        var expectedAuthToken = AuthTokenFixtures.newAuthToken();

        given(jwtManager.extractClaims(refreshToken.value())).willReturn(authCredential);
        given(jwtManager.generate(authCredential)).willReturn(expectedAuthToken);

        // when
        AuthToken authToken = authService.refreshAuthToken(refreshToken);

        // then
        then(refreshTokenValidator).should(times(1)).validate(refreshToken);
        assertThat(authToken).isEqualTo(expectedAuthToken);
    }
}
