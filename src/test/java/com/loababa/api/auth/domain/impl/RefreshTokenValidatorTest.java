package com.loababa.api.auth.domain.impl;

import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.domain.impl.model.RefreshTokenFixtures;
import com.loababa.api.auth.domain.impl.repository.RefreshTokenReader;
import com.loababa.api.auth.exception.InvalidTokenException;
import com.loababa.api.common.MockTestBase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class RefreshTokenValidatorTest extends MockTestBase {

    @InjectMocks
    private RefreshTokenValidator refreshTokenValidator;
    @Mock
    private RefreshTokenReader refreshTokenReader;

    @Nested
    class 리프레쉬_토큰을_검증할_수_있다 {

        @Test
        void 리프레쉬_토큰이_존재할_때() {
            // given
            RefreshToken refreshToken = RefreshTokenFixtures.newRefreshToken();

            given(refreshTokenReader.existRefreshToken(refreshToken)).willReturn(true);

            // when
            // then
            assertThatCode(() -> refreshTokenValidator.validate(refreshToken))
                    .doesNotThrowAnyException();
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
