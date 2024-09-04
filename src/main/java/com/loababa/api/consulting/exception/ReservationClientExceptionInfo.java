package com.loababa.api.consulting.exception;

import com.loababa.api.common.exception.ClientExceptionInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReservationClientExceptionInfo implements ClientExceptionInfo {

    // 400
    NOT_FOUND_RESERVATION("존재하지 않는 예약입니다."),
    BAD_RESERVATION_UPDATE_REQUEST("유효하지 않은 예약 수정 요청입니다."),

    // 403
    FORBIDDEN("해당 작업에 대한 권한이 없습니다.");

    private final String message;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return message;
    }

}
