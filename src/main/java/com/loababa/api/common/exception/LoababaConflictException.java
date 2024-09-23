package com.loababa.api.common.exception;

public class LoababaConflictException extends LoababaException {

    public LoababaConflictException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(clientExceptionInfo, serverExceptionInfo);
    }

}

