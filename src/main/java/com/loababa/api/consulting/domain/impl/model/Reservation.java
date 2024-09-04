package com.loababa.api.consulting.domain.impl.model;

import org.springframework.lang.Nullable;

import java.util.List;

public record Reservation(
        @Nullable
        Long reservationId,
        ReservationPreResponses reservationPreResponses,
        List<ReservationDateTime> reservationDateTimes,
        Long lossamId,
        Long mokokoId
) {

}
