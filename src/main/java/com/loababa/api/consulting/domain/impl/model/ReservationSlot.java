package com.loababa.api.consulting.domain.impl.model;

public record ReservationSlot(
        TimeRange timeRange,
        boolean isAvailable
) {
}
