package com.loababa.api.auth.infra.persistance.adapter;

import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.domain.impl.model.RefreshTokenFixtures;
import com.loababa.api.common.MockTestBase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.cache.Cache;

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

}
