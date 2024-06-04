package com.loababa.api.auth.infra.persistance.adapter;

import com.loababa.api.auth.domain.impl.model.OAuthUser;
import com.loababa.api.auth.domain.impl.repository.OAuthUserReader;
import com.loababa.api.auth.domain.impl.repository.OAuthUserWriter;
import com.loababa.api.auth.infra.persistance.entity.OAuthUserEntity;
import com.loababa.api.auth.infra.persistance.repository.OAuthUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthUserJpaRepositoryAdapter implements OAuthUserReader, OAuthUserWriter {

    private final OAuthUserJpaRepository oAuthUserJpaRepository;

    @Override
    public void save(OAuthUser oAuth) {
        oAuthUserJpaRepository.save(OAuthUserEntity.from(oAuth));
    }

    @Override
    public boolean exists(OAuthUser oAuth) {
        return oAuthUserJpaRepository.existsByOAuthUser(oAuth.oAuthPlatform(), oAuth.oAuthUserId());
    }
}
