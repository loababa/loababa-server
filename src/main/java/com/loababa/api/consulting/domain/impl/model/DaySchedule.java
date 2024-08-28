package com.loababa.api.consulting.domain.impl.model;

import java.time.LocalDate;
import java.util.List;

public record DaySchedule(
        boolean isBusinessDay,
        LocalDate date,
        List<ReservationSlot> slots
) {

    public DaySchedule(LocalDate localDate, List<ReservationSlot> slots) {
        this(true, localDate, slots);
    }

    public static DaySchedule newDayOffSchedule(LocalDate localDate) {
        return new DaySchedule(false, localDate, List.of());
    }

}
