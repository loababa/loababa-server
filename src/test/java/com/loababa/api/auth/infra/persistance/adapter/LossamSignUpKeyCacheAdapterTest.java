package com.loababa.api.auth.infra.persistance.adapter;

import com.loababa.api.auth.domain.member.impl.model.LossamSignUpKey;
import com.loababa.api.auth.infra.persistance.adapter.member.LossamSignUpKeyCacheAdapter;
import com.loababa.api.common.MockTestBase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.cache.Cache;

import static org.mockito.BDDMockito.then;

class LossamSignUpKeyCacheAdapterTest extends MockTestBase {

    @InjectMocks
    private LossamSignUpKeyCacheAdapter lossamSignUpKeyCacheAdapter;

    @Mock
    private Cache signUpKeyCache;

    @Test
    void nanoId를_캐쉬에_저장할_수_있다() {
        // given
        LossamSignUpKey key = new LossamSignUpKey("key");

        // when
        lossamSignUpKeyCacheAdapter.save(key);

        // then
        then(signUpKeyCache).should().put(key.value(), true);
    }
}
