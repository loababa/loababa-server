package com.loababa.api.consulting.domain.impl.model;

import org.springframework.lang.Nullable;

public record ReservationPreResponses(
        @Nullable
        Long reservationPreResponsesId,
        String characterDetails,
        String inquiryDetails,
        String experience,
        String contactNumber
) {

}
