package com.loababa.api.consulting.exception;

import com.loababa.api.common.exception.ClientExceptionInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConsultingClientExceptionInfo implements ClientExceptionInfo {
    INVALID_TIME_RANGE("잘못된 시작 시간, 종료 시간 입력입니다."),
    ;

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
