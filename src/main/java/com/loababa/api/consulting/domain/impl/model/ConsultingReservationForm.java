package com.loababa.api.consulting.domain.impl.model;

import java.util.List;

public record ConsultingReservationForm(
        String characterDetails,
        String inquiryDetails,
        String experience,
        String contactNumber,
        List<DateTimeRange> reservationDateTimes,
        long lossamId,
        long memberId
) {

}
