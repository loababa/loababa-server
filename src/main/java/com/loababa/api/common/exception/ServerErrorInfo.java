package com.loababa.api.common.exception;

public record ServerErrorInfo(
        Throwable cause,
        String message
) {

    public ServerErrorInfo(String message) {
        this(null, message);
    }
}
