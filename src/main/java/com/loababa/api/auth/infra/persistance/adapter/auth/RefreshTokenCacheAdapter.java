package com.loababa.api.auth.infra.persistance.adapter.auth;

import com.loababa.api.auth.domain.auth.impl.model.token.RefreshToken;
import com.loababa.api.auth.domain.auth.impl.repository.RefreshTokenReader;
import com.loababa.api.auth.domain.auth.impl.repository.RefreshTokenWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenCacheAdapter implements RefreshTokenWriter, RefreshTokenReader {

    private final Cache tokenCache;

    public RefreshTokenCacheAdapter(
            @Qualifier("refreshTokenCache") Cache tokenCache
    ) {
        this.tokenCache = tokenCache;
    }

    @Override
    public void save(RefreshToken refreshToken) {
        tokenCache.put(refreshToken, true);
    }

    @Override
    public void invalidateRefreshToken(RefreshToken refreshToken) {
        tokenCache.evict(refreshToken);
    }

    @Override
    public boolean existRefreshToken(RefreshToken refreshToken) {
        Cache.ValueWrapper valueWrapper = tokenCache.get(refreshToken);
        return valueWrapper != null;
    }

}
