package com.loababa.api.auth.infra.persistance.repository;


import com.loababa.api.auth.domain.impl.model.OAuthPlatform;
import com.loababa.api.auth.infra.persistance.entity.OAuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OAuthUserJpaRepository extends JpaRepository<OAuthUserEntity, Long> {

    @Query("""
            SELECT COUNT(e) > 0
            FROM OAuthUserEntity e
            WHERE e.oAuthId = :oAuthUserId AND e.oAuthPlatform = :oAuthPlatform
            """)
    boolean existsByOAuthUser(OAuthPlatform oAuthPlatform, Long oAuthUserId);  // todo: QueryDSL LIMIT 1 사용해서 최적화

}
