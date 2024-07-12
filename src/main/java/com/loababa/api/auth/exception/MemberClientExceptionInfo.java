package com.loababa.api.auth.exception;

import com.loababa.api.common.exception.ClientExceptionInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberClientExceptionInfo implements ClientExceptionInfo {
    // SIGN UP
    DUPLICATE_MEMBER_NICKNAME("중복된 닉네임입니다."),
    INVALID_LOSSAM_SIGN_UP_KEY("회원가입 자격이 없습니다. 궁금한 점이 있으면 관리자에게 문의해 주세요."),
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
