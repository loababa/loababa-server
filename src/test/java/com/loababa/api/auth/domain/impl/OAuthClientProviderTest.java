package com.loababa.api.auth.domain.impl;

import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthClient;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthClientProvider;
import com.loababa.api.auth.domain.impl.model.OAuthClientFixtures;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthPlatform;
import com.loababa.api.common.MockTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class OAuthClientProviderTest extends MockTestBase {

    private OAuthClientProvider oAuthClientProvider;

    @BeforeEach
    @EnumSource(OAuthPlatform.class)
    void setUp() {
        OAuthPlatform[] oAuthPlatforms = OAuthPlatform.values();
        Set<OAuthClient> clients = Arrays.stream(oAuthPlatforms)
                .map(OAuthClientFixtures::fakeOAuthClient)
                .collect(Collectors.toSet());
        oAuthClientProvider = new OAuthClientProvider(clients);
    }


    @ParameterizedTest
    @EnumSource(OAuthPlatform.class)
    void OAuthPlatform를_넣으면_해당_공급자와_통신하는_객체를_반환할_수_있다(OAuthPlatform oAuthPlatform) {
        // given

        // when
        OAuthClient oAuthClient = oAuthClientProvider.getOAuthClient(oAuthPlatform);

        // then
        assertThat(oAuthClient.getOAuthPlatform()).isEqualTo(oAuthPlatform);
    }

}
