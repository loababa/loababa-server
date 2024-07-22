package com.loababa.api.auth.infra.persistance.adapter.member;

import com.loababa.api.auth.domain.member.impl.model.LossamSignUpKey;
import com.loababa.api.auth.domain.member.impl.repository.LossamSignUpKeyReader;
import com.loababa.api.auth.domain.member.impl.repository.LossamSignUpKeyWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class LossamSignUpKeyCacheAdapter implements LossamSignUpKeyReader, LossamSignUpKeyWriter {

    private final Cache signUpKeyCache;

    public LossamSignUpKeyCacheAdapter(
            @Qualifier("lossamSignUpKeyCache") Cache signUpKeyCache
    ) {
        this.signUpKeyCache = signUpKeyCache;
    }

    @Override
    public void save(LossamSignUpKey key) {
        signUpKeyCache.put(key.value(), true);
    }

    @Override
    public boolean existsKey(String key) {
        Boolean exists = signUpKeyCache.get(key, Boolean.class);
        return Boolean.TRUE.equals(exists);
    }

}
