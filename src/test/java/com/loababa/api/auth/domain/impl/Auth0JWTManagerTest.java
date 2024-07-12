package com.loababa.api.auth.domain.impl;

import com.loababa.api.auth.domain.impl.model.AccessToken;
import com.loababa.api.auth.domain.impl.model.AuthToken;
import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.domain.impl.model.JWTProperties;
import com.loababa.api.auth.domain.impl.repository.RefreshTokenWriter;
import com.loababa.api.auth.ui.AuthCredential;
import com.loababa.api.common.MockTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;

class Auth0JWTManagerTest extends MockTestBase {

    private Auth0JWTManager auth0JWTManager;

    @Mock
    private RefreshTokenWriter refreshTokenWriter;

    @BeforeEach
    void setUp() {
        JWTProperties jwtProperties = new JWTProperties("secretKey", 10000, 100000);
        auth0JWTManager = new Auth0JWTManager(jwtProperties, refreshTokenWriter);
    }

    @Test
    void access_token과_refresh_token_생성할_수_있다() {
        //given
        var authCredential = new AuthCredential(1L, 1L);

        // when
        AuthToken authToken = auth0JWTManager.generate(authCredential);

        // then
        assertThat(authToken.accessToken()).isInstanceOf(AccessToken.class);
        assertThat(authToken.refreshToken()).isInstanceOf(RefreshToken.class);
    }

}
