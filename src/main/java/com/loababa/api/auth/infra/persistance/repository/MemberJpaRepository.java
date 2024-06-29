package com.loababa.api.auth.infra.persistance.repository;

import com.loababa.api.auth.infra.persistance.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    boolean existsByNickname(String nickname);

}
