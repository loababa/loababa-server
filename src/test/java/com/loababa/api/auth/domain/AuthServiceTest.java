package com.loababa.api.auth.domain;

import com.loababa.api.auth.domain.impl.JWTManager;
import com.loababa.api.auth.domain.impl.RefreshTokenValidator;
import com.loababa.api.auth.domain.impl.model.AuthToken;
import com.loababa.api.auth.domain.impl.model.AuthTokenFixtures;
import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.domain.impl.model.RefreshTokenFixtures;
import com.loababa.api.auth.domain.impl.repository.RefreshTokenWriter;
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
        RefreshToken refreshToken = RefreshTokenFixtures.newRefreshToken();

        AuthToken expectedAuthToken = AuthTokenFixtures.newAuthToken();
        given(jwtManager.generate()).willReturn(expectedAuthToken);

        // when
        AuthToken authToken = authService.refreshAuthToken(refreshToken);

        // then
        then(refreshTokenValidator).should(times(1)).validate(refreshToken);
        then(refreshTokenWriter).should(times(1)).invalidateRefreshToken(refreshToken);
        then(refreshTokenWriter).should(times(1)).save(refreshToken);

        assertThat(authToken).isEqualTo(expectedAuthToken);
    }
}
