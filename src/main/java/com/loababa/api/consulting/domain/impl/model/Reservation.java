package com.loababa.api.consulting.domain.impl.model;

import org.springframework.lang.Nullable;

import java.util.List;

public record Reservation(
        @Nullable
        Long reservationId,
        ReservationPreResponses reservationPreResponses,
        List<ReservationDateTime> reservationDateTimes,  //TODO 일급컬렉션으로 변경
        Long lossamId,
        Long mokokoId
) {

    public boolean isNew() {
        return reservationId == null &&
                reservationPreResponses().reservationPreResponsesId() == null &&
                reservationDateTimes().stream()
                        .allMatch(reservationDateTime -> reservationDateTime.reservationDateTimeId() == null);
    }

    public boolean isExisting() {
        return reservationId != null ||
                reservationPreResponses().reservationPreResponsesId() != null ||
                reservationDateTimes().stream()
                        .allMatch(reservationDateTime -> reservationDateTime.reservationDateTimeId() != null);
    }

}
