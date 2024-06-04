package com.loababa.api.auth.exception;

import com.loababa.api.common.exception.LoababaException;
import com.loababa.api.common.exception.ServerErrorInfo;

public class ExternalCommunicationException extends LoababaException {

    public ExternalCommunicationException(String clientMessage, ServerErrorInfo serverErrorInfo) {
        super(clientMessage, serverErrorInfo);
    }

}
