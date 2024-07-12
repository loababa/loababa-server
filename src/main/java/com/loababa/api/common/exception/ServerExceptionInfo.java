package com.loababa.api.common.exception;

public record ServerExceptionInfo(
        Throwable cause,
        String message
) {

    public ServerExceptionInfo(String message) {
        this(null, message);
    }
}
