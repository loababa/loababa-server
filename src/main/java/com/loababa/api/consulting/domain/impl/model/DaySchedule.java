package com.loababa.api.consulting.domain.impl.model;

import java.util.List;

public record DaySchedule(
        boolean isBusinessDay,
        List<ReservationSlot> slots
) {

    public DaySchedule(List<ReservationSlot> slots) {
        this(true, slots);
    }

    public static DaySchedule newDayOffSchedule() {
        return new DaySchedule(false, List.of());
    }

}
