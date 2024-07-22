package com.loababa.api.auth.domain.member.impl.model;

import com.loababa.api.auth.domain.member.impl.repository.LossamSignUpKeyReader;
import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.loababa.api.auth.exception.MemberClientExceptionInfo.INVALID_LOSSAM_SIGN_UP_KEY;

@Component
@RequiredArgsConstructor
public class LossamSignUpKeyValidator {

    private final LossamSignUpKeyReader lossamSignUpKeyReader;

    public void validate(String key) {
        if (!lossamSignUpKeyReader.existsKey(key)) {
            throw new LoababaBadRequestException(
                    INVALID_LOSSAM_SIGN_UP_KEY,
                    new ServerExceptionInfo("잘못된 로쌤 회원가입 키입니다.")
            );
        }
    }

}
