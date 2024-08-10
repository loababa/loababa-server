package com.loababa.api.consulting.domain.impl.model;

import java.time.DayOfWeek;
import java.util.Map;

public record LossamSchedule(
        Map<DayOfWeek, TimeRange> schedule
) {
}
