package com.loababa.api.auth.infra.persistance.adapter;

import com.loababa.api.auth.domain.impl.model.RefreshToken;
import com.loababa.api.auth.domain.impl.repository.RefreshTokenWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenCacheAdapter implements RefreshTokenWriter {

    private final Cache tokenCache;

    public RefreshTokenCacheAdapter(
            @Qualifier("tokenCache") Cache tokenCache
    ) {
        this.tokenCache = tokenCache;
    }

    public void save(RefreshToken refreshToken) {
        tokenCache.put(refreshToken, true);
    }

}
