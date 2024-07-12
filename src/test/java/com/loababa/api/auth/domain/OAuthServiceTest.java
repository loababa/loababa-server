package com.loababa.api.auth.domain;

import com.loababa.api.auth.domain.impl.JWTManager;
import com.loababa.api.auth.domain.impl.OAuthClientProvider;
import com.loababa.api.auth.domain.impl.OAuthUserManager;
import com.loababa.api.auth.domain.impl.model.AuthToken;
import com.loababa.api.auth.domain.impl.model.OAuthClientFixtures;
import com.loababa.api.auth.domain.impl.model.OAuthCredential;
import com.loababa.api.auth.domain.impl.model.OAuthPlatform;
import com.loababa.api.auth.domain.impl.model.OAuthToken;
import com.loababa.api.auth.domain.impl.model.OAuthTokenFixtures;
import com.loababa.api.auth.domain.impl.model.OAuthUser;
import com.loababa.api.auth.domain.impl.model.OAuthUserInfo;
import com.loababa.api.auth.domain.impl.model.OAuthUserInfoFixtures;
import com.loababa.api.auth.domain.impl.repository.MemberReader;
import com.loababa.api.auth.ui.AuthCredential;
import com.loababa.api.common.MockTestBase;
import org.instancio.Instancio;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.loababa.api.auth.domain.impl.model.AccessTokenFixtures.newAccessToken;
import static com.loababa.api.auth.domain.impl.model.RefreshTokenFixtures.newRefreshToken;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

class OAuthServiceTest extends MockTestBase {

    @InjectMocks
    private OAuthService oAuthService;

    @Mock
    private OAuthClientProvider oAuthClientProvider;
    @Mock
    private OAuthUserManager oAuthUserManager;
    @Mock
    private JWTManager jwtManager;
    @Mock
    private MemberReader memberReader;

    @ParameterizedTest
    @EnumSource(OAuthPlatform.class)
    void OAuth_인증을_통해_자체_인증_토큰_발급할_수_있다(OAuthPlatform oAuthPlatform) {
        // given
        OAuthCredential oAuthCredential = new OAuthCredential(oAuthPlatform, "code");

        var oAuthClient = OAuthClientFixtures.mockOAuthClient();
        given(oAuthClientProvider.getOAuthClient(oAuthPlatform))
                .willReturn(oAuthClient);

        OAuthToken oAuthToken = OAuthTokenFixtures.newOAuthToken();
        given(oAuthClient.fetchOAuthToken(oAuthCredential.code()))
                .willReturn(oAuthToken);

        OAuthUserInfo oAuthUserInfo = OAuthUserInfoFixtures.newOAuthUserInfo();
        given(oAuthClient.fetchUserOAuthInfo(oAuthToken.accessToken()))
                .willReturn(oAuthUserInfo);

        OAuthUser oAuthUser = new OAuthUser(oAuthPlatform, oAuthUserInfo.id());
        Long oAuthUserId = Instancio.create(Long.class);;
        given(oAuthUserManager.saveOAuthUserIfNotExists(oAuthUser)).willReturn(oAuthUserId);

        Long memberId = Instancio.create(Long.class);
        given(memberReader.getMemberIdByOAuthUserId(oAuthUserId)).willReturn(memberId);

        var authCredential = new AuthCredential(oAuthUserId, memberId);
        var expectedAuthToken = new AuthToken(newAccessToken(), newRefreshToken());
        given(jwtManager.generate(authCredential)).willReturn(expectedAuthToken);


        // when
        AuthToken authToken = oAuthService.authenticate(oAuthCredential);

        // then
        then(oAuthClient).should(times(1)).invalidateOAuthToken(oAuthToken.accessToken());
        then(oAuthUserManager).should(times(1)).saveOAuthUserIfNotExists(oAuthUser);
        assertThat(authToken).isEqualTo(expectedAuthToken);
    }

}
