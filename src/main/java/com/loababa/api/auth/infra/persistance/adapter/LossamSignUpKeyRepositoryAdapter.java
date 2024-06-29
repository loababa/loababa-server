package com.loababa.api.auth.infra.persistance.adapter;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.loababa.api.auth.domain.impl.repository.NanoIdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class LossamSignUpKeyRepositoryAdapter implements NanoIdGenerator {

    private final Cache signUpKeyCache;

    public LossamSignUpKeyRepositoryAdapter(
            @Qualifier("lossamSignUpKeyCache") Cache signUpKeyCache
    ) {
        this.signUpKeyCache = signUpKeyCache;
    }

    @Override
    public String generate() {
        String nanoId = NanoIdUtils.randomNanoId();
        signUpKeyCache.put(nanoId, true);
        return nanoId;
    }

}
