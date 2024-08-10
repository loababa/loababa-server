package com.loababa.api.consulting.persistence.repository;

import com.loababa.api.consulting.persistence.entity.ConsultingScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultingScheduleJpaRepository extends JpaRepository<ConsultingScheduleEntity, Long> {
}
