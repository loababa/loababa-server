package com.loababa.api.common.exception;

public class LoababaBadRequestException extends LoababaException {

    public LoababaBadRequestException(ServerExceptionInfo serverExceptionInfo) {
        super(CommonClientExceptionInfo.BAD_REQUEST, serverExceptionInfo);
    }

    public LoababaBadRequestException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(clientExceptionInfo, serverExceptionInfo);
    }

}
