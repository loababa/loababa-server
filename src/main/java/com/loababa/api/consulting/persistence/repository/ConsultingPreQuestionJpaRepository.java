package com.loababa.api.consulting.persistence.repository;

import com.loababa.api.consulting.persistence.entity.ReservationPreResponsesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultingPreQuestionJpaRepository extends JpaRepository<ReservationPreResponsesEntity, Long> {

}
