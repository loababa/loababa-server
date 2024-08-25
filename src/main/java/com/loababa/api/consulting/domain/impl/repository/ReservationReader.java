package com.loababa.api.consulting.domain.impl.repository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
public interface ReservationReader {

    boolean existsReservation(long lossamId, LocalDateTime slotStartTime, LocalDateTime slotEndTime);

}
