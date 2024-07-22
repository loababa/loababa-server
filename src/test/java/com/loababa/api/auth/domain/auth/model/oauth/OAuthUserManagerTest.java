package com.loababa.api.auth.domain.auth.model.oauth;

import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthPlatform;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthUser;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthUserManager;
import com.loababa.api.auth.domain.auth.impl.repository.OAuthUserReader;
import com.loababa.api.auth.domain.auth.impl.repository.OAuthUserWriter;
import com.loababa.api.common.MockTestBase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;


class OAuthUserManagerTest extends MockTestBase {

    @InjectMocks
    private OAuthUserManager oAuthUserManager;

    @Mock
    private OAuthUserWriter oAuthUserWriter;
    @Mock
    private OAuthUserReader oAuthUserReader;


    @Nested
    class 로그인_기록을_바탕으로_OAuthUser를_저장할_수_있다 {

        @ParameterizedTest
        @EnumSource(OAuthPlatform.class)
        void OAuth_로그인을_한_기록이_없을_때(OAuthPlatform oAuthplatform) {
            // given
            Long id = 1L;

            OAuthUser oAuthUser = new OAuthUser(oAuthplatform, id);
            given(oAuthUserReader.isRegisteredAuthUser(oAuthUser)).willReturn(false);

            // when
            oAuthUserManager.saveOAuthUserIfNotExists(oAuthUser);

            // then
            then(oAuthUserWriter).should(times(1)).save(oAuthUser);
        }

        @ParameterizedTest
        @EnumSource(OAuthPlatform.class)
        void OAuth_로그인을_한_기록이_있을_때(OAuthPlatform oAuthplatform) {
            // given
            Long id = 1L;

            OAuthUser oAuthUser = new OAuthUser(oAuthplatform, id);
            given(oAuthUserReader.isRegisteredAuthUser(oAuthUser)).willReturn(true);

            // when
            oAuthUserManager.saveOAuthUserIfNotExists(oAuthUser);

            // then
            then(oAuthUserWriter).should(never()).save(oAuthUser);
        }

    }

}
