package com.loababa.api.common.exception;

public class LoababaException extends RuntimeException {

    private final ClientExceptionInfo clientExceptionInfo;

    public LoababaException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(serverExceptionInfo.message(), serverExceptionInfo.cause());
        this.clientExceptionInfo = clientExceptionInfo;
    }

    public String getClientCode() {
        return clientExceptionInfo.getCode();
    }

    public String getClientMessage() {
        return clientExceptionInfo.getMessage();
    }

}
