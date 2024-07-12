package com.loababa.api.common.exception;

public class LoababaForbiddenException extends LoababaException{

    public LoababaForbiddenException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(clientExceptionInfo, serverExceptionInfo);
    }

}
