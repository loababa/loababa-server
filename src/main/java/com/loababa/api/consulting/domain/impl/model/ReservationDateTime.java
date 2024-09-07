package com.loababa.api.consulting.domain.impl.model;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record ReservationDateTime(
        @Nullable
        Long reservationDateTimeId,
        DateTimeRange dateTimeRange
) {

    public LocalDateTime startTime() {
        return dateTimeRange().start();
    }

    public LocalDateTime endTime() {
        return dateTimeRange().end();
    }

}
