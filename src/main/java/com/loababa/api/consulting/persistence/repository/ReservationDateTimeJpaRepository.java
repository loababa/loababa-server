package com.loababa.api.consulting.persistence.repository;

import com.loababa.api.consulting.persistence.entity.ReservationDateTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReservationDateTimeJpaRepository extends JpaRepository<ReservationDateTimeEntity, Long> {

    List<ReservationDateTimeEntity> findByReservationIdIn(Collection<Long> reservationId);

}
