package com.loababa.api.common.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommonClientErrorInfo implements ClientErrorInfo{
    // DISCORD
    DISCORD_COMMUNICATION_FAIL("디스코드 서버 연결에 실패헀습니다.")
    ;

    private final String clientMessage;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return clientMessage;
    }
}
