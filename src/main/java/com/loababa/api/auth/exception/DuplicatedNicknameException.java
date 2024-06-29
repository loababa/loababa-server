package com.loababa.api.auth.exception;

import com.loababa.api.common.exception.ClientErrorInfo;
import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerErrorInfo;

public class DuplicatedNicknameException extends LoababaBadRequestException {

    public DuplicatedNicknameException(ClientErrorInfo clientErrorInfo, ServerErrorInfo serverErrorInfo) {
        super(clientErrorInfo, serverErrorInfo);
    }

}
