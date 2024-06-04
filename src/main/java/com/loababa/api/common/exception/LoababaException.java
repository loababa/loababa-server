package com.loababa.api.common.exception;

import lombok.Getter;

@Getter
public class LoababaException extends RuntimeException {

    private final String clientErrorMessage;

    public LoababaException(String clientErrorMessage, ServerErrorInfo serverErrorInfo) {
        super(serverErrorInfo.message(), serverErrorInfo.cause());
        this.clientErrorMessage = clientErrorMessage;
    }

}
