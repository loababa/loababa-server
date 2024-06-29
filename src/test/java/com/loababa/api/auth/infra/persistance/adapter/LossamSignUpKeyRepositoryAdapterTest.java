package com.loababa.api.auth.infra.persistance.adapter;

import com.loababa.api.common.MockTestBase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.cache.Cache;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.then;

class LossamSignUpKeyRepositoryAdapterTest extends MockTestBase {

    @InjectMocks
    private LossamSignUpKeyRepositoryAdapter lossamSignUpKeyRepositoryAdapter;

    @Mock
    private Cache signUpKeyCache;

    @Test
    void nanoId를_생성하고_캐쉬에_저장할_수_있다() {
        // given


        // when
        String nanoId = lossamSignUpKeyRepositoryAdapter.generate();

        // then
        then(signUpKeyCache).should().put(nanoId, true);
        assertThat(nanoId).isNotBlank();
    }
}
