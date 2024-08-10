package com.loababa.api.consulting.domain.impl.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

public final class LossamScheduleFixtures {

    public static LossamSchedule newLossamSchedule() {
        Map<DayOfWeek, TimeRange> schedule = new EnumMap<>(DayOfWeek.class);
        schedule.put(DayOfWeek.MONDAY, new TimeRange(LocalTime.of(9, 0), LocalTime.of(17, 0)));
        schedule.put(DayOfWeek.TUESDAY, new TimeRange(LocalTime.of(9, 0), LocalTime.of(17, 0)));
        schedule.put(DayOfWeek.WEDNESDAY, new TimeRange(LocalTime.of(9, 0), LocalTime.of(17, 0)));
        schedule.put(DayOfWeek.THURSDAY, new TimeRange(LocalTime.of(9, 0), LocalTime.of(17, 0)));
        schedule.put(DayOfWeek.FRIDAY, new TimeRange(LocalTime.of(9, 0), LocalTime.of(17, 0)));
        schedule.put(DayOfWeek.SATURDAY, new TimeRange(LocalTime.of(10, 0), LocalTime.of(15, 0)));
        schedule.put(DayOfWeek.SUNDAY, new TimeRange(LocalTime.of(10, 0), LocalTime.of(15, 0)));
        return new LossamSchedule(schedule);
    }

}
