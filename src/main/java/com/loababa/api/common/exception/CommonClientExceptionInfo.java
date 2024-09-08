package com.loababa.api.common.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommonClientExceptionInfo implements ClientExceptionInfo {
    // COMMON
    BAD_REQUEST("유효하지 않은 요청입니다. 관리자에게 문의하세요"),

    // DISCORD
    DISCORD_COMMUNICATION_FAIL("디스코드 서버 연결에 실패헀습니다."),

    // AWS S3
    UNKNOWN_AWS_S3_FOLDER("존재하지 않은 폴더입니다."),
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
