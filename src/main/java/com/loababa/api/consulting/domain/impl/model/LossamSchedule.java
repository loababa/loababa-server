package com.loababa.api.consulting.domain.impl.model;

import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import com.loababa.api.consulting.exception.ConsultingClientExceptionInfo;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public record LossamSchedule(
        Map<DayOfWeek, TimeRange> schedule
) {
    public LossamSchedule {
        Set<DayOfWeek> allDays = EnumSet.allOf(DayOfWeek.class);
        if (!schedule.keySet().containsAll(allDays)) {
            throw new LoababaBadRequestException(
                    ConsultingClientExceptionInfo.INVALID_DAY_OF_WEEK,
                    new ServerExceptionInfo(ConsultingClientExceptionInfo.INVALID_DAY_OF_WEEK.getMessage())
            );
        }
    }
}
