package com.loababa.api.auth.exception;

import com.loababa.api.common.exception.ClientExceptionInfo;
import com.loababa.api.common.exception.LoababaBadGatewayException;
import com.loababa.api.common.exception.ServerExceptionInfo;

public class ExternalCommunicationException extends LoababaBadGatewayException {

    public ExternalCommunicationException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(clientExceptionInfo, serverExceptionInfo);
    }

}
