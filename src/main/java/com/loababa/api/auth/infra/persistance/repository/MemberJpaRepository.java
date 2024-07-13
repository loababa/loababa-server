package com.loababa.api.auth.infra.persistance.repository;

import com.loababa.api.auth.infra.persistance.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    boolean existsByNickname(String nickname);

    @Query("""
            SELECT m.id
            FROM MemberEntity m
            WHERE m.oAuthUserId = :oAuthUserId
            """)
    Long findIdByOAuthUserId(Long oAuthUserId);

}
