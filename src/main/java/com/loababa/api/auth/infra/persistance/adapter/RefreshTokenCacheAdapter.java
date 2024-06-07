package com.loababa.api.auth.infra.persistance.adapter;

import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.domain.impl.repository.RefreshTokenReader;
import com.loababa.api.auth.domain.impl.repository.RefreshTokenWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenCacheAdapter implements RefreshTokenWriter, RefreshTokenReader {

    private final Cache tokenCache;

    public RefreshTokenCacheAdapter(
            @Qualifier("tokenCache") Cache tokenCache
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
