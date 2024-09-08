package com.loababa.api.consulting.ui.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReservationApproveReq(
        @Positive
        @NotNull
        Long dateTimeId
) {
}
