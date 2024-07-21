package com.loababa.api.auth.infra.persistance.repository;

import com.loababa.api.auth.infra.persistance.entity.LostArkCharacterInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LossamLostArkCharacterInfoJpaRepository extends JpaRepository<LostArkCharacterInfoEntity, Long> {
}
