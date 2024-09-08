package com.loababa.api.consulting.domain.impl.model;

import com.loababa.api.auth.domain.member.impl.model.MemberType;
import org.springframework.lang.Nullable;

import java.util.List;

public record ReservationListForms(
        List<ReservationListForm> reservationListForms,
        MemberType memberType
) {
    public static ReservationListForms from(List<ReservationListForm> reservationListForms, MemberType memberType) {
        return new ReservationListForms(reservationListForms, memberType);
    }

    public record ReservationListForm(
            Long id,
            long lossamId,
            long mokokoId,
            @Nullable String inquiryDetails,
            List<DateTimeRange> dateTimeRanges
    ) {
    }

}
