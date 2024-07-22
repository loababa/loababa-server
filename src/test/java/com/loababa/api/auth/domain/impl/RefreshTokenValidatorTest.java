package com.loababa.api.auth.domain.impl;

import com.loababa.api.auth.domain.auth.impl.model.token.RefreshToken;
import com.loababa.api.auth.domain.auth.impl.model.token.RefreshTokenValidator;
import com.loababa.api.auth.domain.impl.model.RefreshTokenFixtures;
import com.loababa.api.auth.domain.auth.impl.repository.RefreshTokenReader;
import com.loababa.api.auth.domain.auth.impl.repository.RefreshTokenWriter;
import com.loababa.api.auth.exception.InvalidTokenException;
import com.loababa.api.common.MockTestBase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

class RefreshTokenValidatorTest extends MockTestBase {

    @InjectMocks
    private RefreshTokenValidator refreshTokenValidator;
    @Mock
    private RefreshTokenReader refreshTokenReader;
    @Mock
    private RefreshTokenWriter refreshTokenWriter;

    @Nested
    class 리프레쉬_토큰을_검증할_수_있다 {

        @Test
        void 리프레쉬_토큰이_존재할_때() {
            // given
            RefreshToken refreshToken = RefreshTokenFixtures.newRefreshToken();

            given(refreshTokenReader.existRefreshToken(refreshToken)).willReturn(true);

            // when
            refreshTokenValidator.validate(refreshToken);

            // then
            then(refreshTokenWriter).should(times(1)).invalidateRefreshToken(refreshToken);
        }

        @Test
        void 리프레쉬_토큰이_존재하지_않을_때() {
            // given
            RefreshToken refreshToken = RefreshTokenFixtures.newRefreshToken();

            given(refreshTokenReader.existRefreshToken(refreshToken)).willReturn(false);

            // when
            // then
            assertThatThrownBy(() -> refreshTokenValidator.validate(refreshToken))
                    .isInstanceOf(InvalidTokenException.class);
        }

    }

}
