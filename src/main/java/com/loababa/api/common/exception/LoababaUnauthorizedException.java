package com.loababa.api.common.exception;

public class LoababaUnauthorizedException extends LoababaException{

    public LoababaUnauthorizedException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(clientExceptionInfo, serverExceptionInfo);
    }

}
