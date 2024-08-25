package com.loababa.api.consulting.domain.impl.model;

import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import org.springframework.lang.Nullable;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import static com.loababa.api.consulting.exception.ConsultingClientExceptionInfo.INVALID_DAY_OF_WEEK;

public record LossamSchedule(
        Map<DayOfWeek, TimeRange> schedule
) {
    public LossamSchedule {
        Set<DayOfWeek> allDays = EnumSet.allOf(DayOfWeek.class);
        if (!schedule.keySet().containsAll(allDays)) {
            throw new LoababaBadRequestException(
                    INVALID_DAY_OF_WEEK,
                    new ServerExceptionInfo(INVALID_DAY_OF_WEEK.getMessage())
            );
        }
    }

    @Nullable // 해당 요일에 상담이 불가능 할 경우
    public TimeRange getTimeRange(DayOfWeek dayOfWeek) {
        return schedule.get(dayOfWeek);
    }

}
