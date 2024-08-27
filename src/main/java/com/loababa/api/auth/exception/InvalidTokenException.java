package com.loababa.api.auth.exception;

import com.loababa.api.common.exception.ClientExceptionInfo;
import com.loababa.api.common.exception.loababaUnauthorizedException;
import com.loababa.api.common.exception.ServerExceptionInfo;

public class InvalidTokenException extends loababaUnauthorizedException {

    public InvalidTokenException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(clientExceptionInfo, serverExceptionInfo);
    }

}
