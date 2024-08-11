package com.loababa.api.consulting.ui.dto;

import com.loababa.api.consulting.domain.impl.model.TimeRange;

import java.time.DayOfWeek;
import java.util.Map;

public record ConsultingScheduleRes(
        Map<DayOfWeek, TimeRange> schedule
) {

}
