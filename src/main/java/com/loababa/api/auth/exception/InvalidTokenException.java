package com.loababa.api.auth.exception;

import com.loababa.api.common.exception.LoababaException;
import com.loababa.api.common.exception.ServerErrorInfo;

public class InvalidTokenException extends LoababaException {

    public InvalidTokenException(String clientErrorMessage, ServerErrorInfo serverErrorInfo) {
        super(clientErrorMessage, serverErrorInfo);
    }

}
