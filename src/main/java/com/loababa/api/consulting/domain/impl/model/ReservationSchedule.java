package com.loababa.api.consulting.domain.impl.model;

import java.util.ArrayList;
import java.util.List;

public record ReservationSchedule(
        List<DaySchedule> value
) {

    public ReservationSchedule() {
        this(new ArrayList<>());
    }

    public void add(DaySchedule daySchedule) {
        value.add(daySchedule);
    }

}
