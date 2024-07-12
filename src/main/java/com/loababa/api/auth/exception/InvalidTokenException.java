package com.loababa.api.auth.exception;

import com.loababa.api.common.exception.ClientExceptionInfo;
import com.loababa.api.common.exception.LoababaForbiddenException;
import com.loababa.api.common.exception.ServerExceptionInfo;

public class InvalidTokenException extends LoababaForbiddenException {

    public InvalidTokenException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(clientExceptionInfo, serverExceptionInfo);
    }

}
