package com.loababa.api.consulting.domain.impl.repository;

import com.loababa.api.consulting.domain.impl.model.Reservation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReservationWriter {

    void upsert(Reservation reservation);

}
