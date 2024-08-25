package com.loababa.api.consulting.domain.impl.model;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public record ReservationSchedule(
        Map<LocalDate, DaySchedule> value
) {

    public ReservationSchedule() {
        this(new LinkedHashMap<>());
    }

    public void put(LocalDate date, DaySchedule daySchedule) {
        value.put(date, daySchedule);
    }

}
