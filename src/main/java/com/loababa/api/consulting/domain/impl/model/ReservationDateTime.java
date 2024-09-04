package com.loababa.api.consulting.domain.impl.model;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record ReservationDateTime(
        @Nullable
        Long reservationDateTimeId,
        DateTimeRange dateTimeRange
) {

    public LocalDateTime getStartTime() {
        return dateTimeRange().start();
    }

    public LocalDateTime getEndTime() {
        return dateTimeRange().end();
    }

}
