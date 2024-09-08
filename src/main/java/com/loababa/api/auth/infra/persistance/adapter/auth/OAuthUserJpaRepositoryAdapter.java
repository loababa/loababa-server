package com.loababa.api.auth.infra.persistance.adapter.auth;

import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthUser;
import com.loababa.api.auth.domain.auth.impl.repository.OAuthUserReader;
import com.loababa.api.auth.domain.auth.impl.repository.OAuthUserWriter;
import com.loababa.api.auth.infra.persistance.entity.OAuthUserEntity;
import com.loababa.api.auth.infra.persistance.repository.OAuthUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OAuthUserJpaRepositoryAdapter implements OAuthUserReader, OAuthUserWriter {

    private final OAuthUserJpaRepository oAuthUserJpaRepository;

    @Transactional
    @Override
    public Long save(OAuthUser oAuthUser) {
        return oAuthUserJpaRepository.save(OAuthUserEntity.from(oAuthUser)).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isRegisteredAuthUser(OAuthUser oAuthUser) {
        return oAuthUserJpaRepository.existsByOAuthUser(oAuthUser.oAuthPlatform(), oAuthUser.oAuthId());
    }

    @Transactional(readOnly = true)
    @Override
    public Long getId(OAuthUser oAuthUser) {
        return oAuthUserJpaRepository.findIdByOAuthUser(oAuthUser.oAuthId(), oAuthUser.oAuthPlatform());
    }
}
