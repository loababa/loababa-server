package com.loababa.api.mentoring.persistence.repository;

import com.loababa.api.mentoring.persistence.entity.MentoringPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoringPostJpaRepository extends JpaRepository<MentoringPostEntity, Long> {
}
