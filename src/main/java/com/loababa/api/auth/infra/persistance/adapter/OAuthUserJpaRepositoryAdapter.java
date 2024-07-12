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
    public Long save(OAuthUser oAuthUser) {
        return oAuthUserJpaRepository.save(OAuthUserEntity.from(oAuthUser)).getId();
    }

    @Override
    public boolean isRegisteredAuthUser(OAuthUser oAuthUser) {
        return oAuthUserJpaRepository.existsByOAuthUser(oAuthUser.oAuthPlatform(), oAuthUser.oAuthId());
    }

    @Override
    public Long getId(OAuthUser oAuthUser) {
        return oAuthUserJpaRepository.findIdByOAuthUser(oAuthUser.oAuthId(), oAuthUser.oAuthPlatform());
    }
}
