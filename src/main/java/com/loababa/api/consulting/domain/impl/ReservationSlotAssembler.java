package com.loababa.api.consulting.domain.impl;

import com.loababa.api.consulting.domain.impl.model.DaySchedule;
import com.loababa.api.consulting.domain.impl.model.LossamSchedule;
import com.loababa.api.consulting.domain.impl.model.ReservationSchedule;
import com.loababa.api.consulting.domain.impl.model.ReservationSlot;
import com.loababa.api.consulting.domain.impl.model.SlotAvailabilityChecker;
import com.loababa.api.consulting.domain.impl.model.TimeRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.loababa.api.consulting.constant.TimePolicy.RESERVATION_SLOT_INTERVAL;
import static com.loababa.api.consulting.domain.impl.model.DaySchedule.newDayOffSchedule;

@Component
@RequiredArgsConstructor
public class ReservationSlotAssembler {

    private final SlotAvailabilityChecker slotAvailabilityChecker;

    public ReservationSchedule assembleReservationSchedule(
            long lossamId,
            LocalDate startDate,
            LocalDate endDate,
            LossamSchedule lossamSchedule
    ) {
        ReservationSchedule reservationSchedule = new ReservationSchedule();

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            TimeRange timeRange = lossamSchedule.getTimeRange(date.getDayOfWeek());
            if (timeRange == null) {
                reservationSchedule.put(date, newDayOffSchedule());
                continue;
            }
            LocalDateTime lossamAvailableStartTime = LocalDateTime.of(date, timeRange.start());
            LocalDateTime lossamAvailableEndTime = LocalDateTime.of(date, timeRange.end());

            List<ReservationSlot> reservationSlots = assembleDailyReservationSlots(lossamId, lossamAvailableStartTime, lossamAvailableEndTime);
            reservationSchedule.put(date, new DaySchedule(reservationSlots));
        }
        return reservationSchedule;
    }

    private List<ReservationSlot> assembleDailyReservationSlots(
            long lossamId,
            LocalDateTime lossamAvailableStartTime,
            LocalDateTime lossamAvailableEndTime
    ) {
        List<ReservationSlot> reservationSlots = new ArrayList<>();
        LocalDateTime slotStartTime = LocalDateTime.from(lossamAvailableStartTime);
        LocalDateTime slotEndTime = slotStartTime.plus(RESERVATION_SLOT_INTERVAL);

        while (slotEndTime.isBefore(lossamAvailableEndTime)) {
            boolean isAvailableSlot = slotAvailabilityChecker.isAvailableSlot(lossamId, slotStartTime, slotEndTime);
            reservationSlots.add(new ReservationSlot(slotStartTime, slotEndTime, isAvailableSlot));

            slotStartTime = LocalDateTime.from(slotEndTime);
            slotEndTime = slotEndTime.plus(RESERVATION_SLOT_INTERVAL);
        }
        return reservationSlots;
    }

}
