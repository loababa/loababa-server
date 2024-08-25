package com.loababa.api.consulting.domain.impl.model;

import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import com.loababa.api.consulting.exception.ConsultingClientExceptionInfo;

import java.time.LocalDateTime;

public record DateTimeRange(
        LocalDateTime start,
        LocalDateTime end
) {
    public DateTimeRange {
        if (start == null || end == null) {
            throw new LoababaBadRequestException(
                    ConsultingClientExceptionInfo.INVALID_TIME_RANGE,
                    new ServerExceptionInfo("시작 시간 혹은 종료 시간이 null 입니다, start: " + start + "end: " + end)
            );
        }
    }
}
