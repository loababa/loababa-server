package com.loababa.api.auth.exception;

import com.loababa.api.common.exception.ClientExceptionInfo;
import com.loababa.api.common.exception.LoababaConflictException;
import com.loababa.api.common.exception.ServerExceptionInfo;

public class DuplicatedNicknameException extends LoababaConflictException {

    public DuplicatedNicknameException(ClientExceptionInfo clientExceptionInfo, ServerExceptionInfo serverExceptionInfo) {
        super(clientExceptionInfo, serverExceptionInfo);
    }

}
