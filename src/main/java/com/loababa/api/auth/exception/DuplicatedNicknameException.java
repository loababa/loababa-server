package com.loababa.api.auth.exception;

import com.loababa.api.common.exception.ClientExceptionInfo;
import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;

public class DuplicatedNicknameException extends LoababaBadRequestException {

    public DuplicatedNicknameException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(clientExceptionInfo, serverExceptionInfo);
    }

}
