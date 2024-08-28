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
import java.time.LocalTime;
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
                reservationSchedule.add(newDayOffSchedule(date));
                continue;
            }
            LocalTime lossamAvailableStartTime = timeRange.start();
            LocalTime lossamAvailableEndTime = timeRange.end();

            List<ReservationSlot> reservationSlots = assembleDailyReservationSlots(lossamId, date, lossamAvailableStartTime, lossamAvailableEndTime);
            reservationSchedule.add(new DaySchedule(date, reservationSlots));
        }
        return reservationSchedule;
    }

    private List<ReservationSlot> assembleDailyReservationSlots(
            long lossamId,
            LocalDate targetDate,
            LocalTime lossamAvailableStartTime,
            LocalTime lossamAvailableEndTime
    ) {
        List<ReservationSlot> reservationSlots = new ArrayList<>();

        for (LocalTime time = lossamAvailableStartTime; time.isBefore(lossamAvailableEndTime); time = time.plus(RESERVATION_SLOT_INTERVAL)) {
            boolean isAvailableSlot = slotAvailabilityChecker.isAvailableSlot(
                    lossamId,
                    LocalDateTime.of(targetDate, lossamAvailableStartTime),
                    LocalDateTime.of(targetDate, lossamAvailableEndTime)
            );

            TimeRange timeRange = new TimeRange(time, time.plus(RESERVATION_SLOT_INTERVAL));
            reservationSlots.add(new ReservationSlot(timeRange, isAvailableSlot));
        }
        return reservationSlots;
    }

}
