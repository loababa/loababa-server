package com.loababa.api.consulting.domain.impl.model;

import com.loababa.api.consulting.domain.impl.repository.ReservationReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SlotAvailabilityChecker {

    private final ReservationReader reservationReader;

    @Transactional(readOnly = true)
    public boolean isAvailableSlot(long lossamId, LocalDateTime slotStartTime, LocalDateTime slotEndTime) {
        return !reservationReader.existsReservation(lossamId, slotStartTime, slotEndTime);
    }

}
