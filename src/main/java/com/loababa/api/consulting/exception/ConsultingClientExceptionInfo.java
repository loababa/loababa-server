package com.loababa.api.consulting.exception;

import com.loababa.api.common.exception.ClientExceptionInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConsultingClientExceptionInfo implements ClientExceptionInfo {
    INVALID_TIME_RANGE("잘못된 시작 시간, 종료 시간 입력입니다."),
    INVALID_DAY_OF_WEEK("모든 요일(월요일부터 일요일까지)이 포함되어야 합니다.")
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
