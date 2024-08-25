package com.loababa.api.consulting.domain.impl.model;

import java.time.LocalDateTime;

public record ReservationSlot(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        boolean isAvailable
) {
}
