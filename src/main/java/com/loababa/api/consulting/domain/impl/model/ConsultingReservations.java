package com.loababa.api.consulting.domain.impl.model;

import com.loababa.api.auth.domain.member.impl.model.MemberType;
import org.springframework.lang.Nullable;

import java.util.List;

public record ConsultingReservations(
        List<ConsultingReservation> consultingReservations,
        MemberType memberType
) {
    public static ConsultingReservations from(List<ConsultingReservation> consultingReservations, MemberType memberType) {
        return new ConsultingReservations(consultingReservations, memberType);
    }

    public record ConsultingReservation(
            Long id,
            long lossamId,
            long mokokoId,
            @Nullable String inquiryDetails,
            List<DateTimeRange> dateTimeRanges
    ) {
    }

}
