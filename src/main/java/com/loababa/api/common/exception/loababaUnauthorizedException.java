package com.loababa.api.common.exception;

public class loababaUnauthorizedException extends LoababaException{

    public loababaUnauthorizedException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(clientExceptionInfo, serverExceptionInfo);
    }

}
