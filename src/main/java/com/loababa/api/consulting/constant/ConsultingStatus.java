package com.loababa.api.consulting.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConsultingStatus {
    PENDING("대기"),
    CONFIRMED("확정"),
    PAST("지난"),
    CANCELLED("취소")
    ;

    private final String description;

    public static ConsultingStatus from(String consultingStatus) {
        return ConsultingStatus.valueOf(
                consultingStatus.toUpperCase()
        );
    }

}
