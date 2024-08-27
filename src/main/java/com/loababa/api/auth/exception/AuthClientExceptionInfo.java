package com.loababa.api.auth.exception;

import com.loababa.api.common.exception.ClientExceptionInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthClientExceptionInfo implements ClientExceptionInfo {
    // AUTHENTICATION
    LOGIN_REQUIRED("서비스를 이용하려면 로그인이 필요합니다."),

    // TOKEN
    INVALID_TOKEN("인증 정보가 잘못되었습니다. 로그인 후 다시 시도해 주세요."),
    EXPIRED_TOKEN("만료된 토큰입니다."),

    // KAKAO_OAUTH
    KAKAO_COMMUNICATION_FAIL("카카오 서버 연결에 실패헀습니다."),
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
