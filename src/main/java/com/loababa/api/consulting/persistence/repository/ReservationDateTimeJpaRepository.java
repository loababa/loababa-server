package com.loababa.api.consulting.persistence.repository;

import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import com.loababa.api.consulting.persistence.entity.ReservationDateTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

import static com.loababa.api.consulting.exception.ReservationClientExceptionInfo.NOT_FOUND_RESERVATION;

public interface ReservationDateTimeJpaRepository extends JpaRepository<ReservationDateTimeEntity, Long> {

    List<ReservationDateTimeEntity> findByReservationIdIn(Collection<Long> reservationId);

    List<ReservationDateTimeEntity> findByReservationId(Long reservationId);

    default ReservationDateTimeEntity getReservationDateTimeEntityById(Long id) {
        return findById(id)
                .orElseThrow(() -> new LoababaBadRequestException(
                        NOT_FOUND_RESERVATION,
                        new ServerExceptionInfo("존재 하지 않는 Reservation DateTime id: " + id)
                ));
    }

}
