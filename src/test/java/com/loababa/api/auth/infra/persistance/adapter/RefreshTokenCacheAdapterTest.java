package com.loababa.api.auth.infra.persistance.adapter;

import com.loababa.api.auth.domain.auth.impl.model.token.RefreshToken;
import com.loababa.api.auth.domain.impl.model.RefreshTokenFixtures;
import com.loababa.api.auth.infra.persistance.adapter.auth.RefreshTokenCacheAdapter;
import com.loababa.api.common.MockTestBase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.cache.Cache;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

class RefreshTokenCacheAdapterTest extends MockTestBase {

    @InjectMocks
    private RefreshTokenCacheAdapter authTokenCacheAdapter;

    @Mock
    private Cache tokenCache;

    @Test
    void RefreshToken을_저장할_수_있다() {
        // given
        RefreshToken refreshToken = RefreshTokenFixtures.newRefreshToken();

        // when
        authTokenCacheAdapter.save(refreshToken);

        // then
        then(tokenCache).should(times(1)).put(refreshToken, true);
    }

    @Test
    void RefreshToken을_무효화할_수_있다() {
        // given
        RefreshToken refreshToken = RefreshTokenFixtures.newRefreshToken();

        // when
        authTokenCacheAdapter.invalidateRefreshToken(refreshToken);

        // then
        then(tokenCache).should(times(1)).evict(refreshToken);
    }

    @Nested
    class RefreshToken이_존재하는지_알_수_있다 {

        @Test
        void RefreshToken이_존재할_경우() {
            // given
            RefreshToken refreshToken = RefreshTokenFixtures.newRefreshToken();

            given(tokenCache.get(refreshToken)).willReturn(ArgumentMatchers::notNull);

            // when
            boolean existed = authTokenCacheAdapter.existRefreshToken(refreshToken);

            // then
            assertThat(existed).isTrue();
        }

        @Test
        void RefreshToken이_존재하지_않을_경우() {
            // given
            RefreshToken refreshToken = RefreshTokenFixtures.newRefreshToken();

            given(tokenCache.get(refreshToken)).willReturn(null);

            // when
            boolean existed = authTokenCacheAdapter.existRefreshToken(refreshToken);

            // then
            assertThat(existed).isFalse();
        }

    }

}
