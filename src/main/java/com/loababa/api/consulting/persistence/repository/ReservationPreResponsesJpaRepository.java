package com.loababa.api.consulting.persistence.repository;

import com.loababa.api.consulting.persistence.entity.ReservationPreResponsesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReservationPreResponsesJpaRepository extends JpaRepository<ReservationPreResponsesEntity, Long> {

    List<ReservationPreResponsesEntity> findByReservationIdIn(Collection<Long> reservationId);

}
