package com.loababa.api.consulting.persistence.repository;

import com.loababa.api.consulting.persistence.entity.ReservationDateTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDateTimeJpaRepository extends JpaRepository<ReservationDateTimeEntity, Long> {
}
