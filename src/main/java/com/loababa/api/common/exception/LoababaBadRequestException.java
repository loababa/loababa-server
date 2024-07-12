package com.loababa.api.common.exception;

public class LoababaBadRequestException extends LoababaException {

    public LoababaBadRequestException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(clientExceptionInfo, serverExceptionInfo);
    }

}
