package com.loababa.api.auth.exception;

import com.loababa.api.common.exception.ClientErrorInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberClientErrorInfo implements ClientErrorInfo {
    DUPLICATE_MEMBER_NICKNAME("중복된 닉네임입니다."),
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
