package com.loababa.api.consulting.domain.impl.repository;

import com.loababa.api.consulting.domain.impl.model.Reservation;

public interface ReservationWriter {

    void upsert(Reservation reservation);

    void approve(Long reservationId, Long dateTimeId);

    void reject(Long reservationId);

}
