package com.loababa.api.common.exception;

public class LoababaBadGatewayException extends LoababaException {

    public LoababaBadGatewayException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(clientExceptionInfo, serverExceptionInfo);
    }

}
