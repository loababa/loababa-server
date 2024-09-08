package com.loababa.api.consulting.domain.impl.model;

import com.loababa.api.auth.domain.member.impl.model.MemberType;
import org.springframework.lang.Nullable;

import java.util.List;

public record ReservationListForms( // TODO 이거 굳이 하나 더 만들기 보단 Reservation 으로 쓰는 게 나을 것 같음
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
