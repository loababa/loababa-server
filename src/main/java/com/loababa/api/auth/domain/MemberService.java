package com.loababa.api.auth.domain;

import com.loababa.api.auth.domain.impl.model.LossamSignUpURLGenerator;
import com.loababa.api.auth.domain.impl.repository.MemberReader;
import com.loababa.api.auth.domain.impl.repository.NanoIdGenerator;
import com.loababa.api.auth.exception.DuplicatedNicknameException;
import com.loababa.api.common.exception.ServerErrorInfo;
import com.loababa.api.common.service.impl.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.loababa.api.auth.exception.MemberClientErrorInfo.DUPLICATE_MEMBER_NICKNAME;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final String LOSSAM_SIGN_UP_URL = "https://localhost:8080/sign-up?key=%s";

    private final MemberReader memberReader;
    private final LossamSignUpURLGenerator lossamSignUpURLGenerator;
    private final NanoIdGenerator nanoIdGenerator;
    private final MessageSender messageSender;

    public void generateLossamSignupURL() {
        final String nanoId = nanoIdGenerator.generate();
        String lossamSignUpURL = lossamSignUpURLGenerator.generate(nanoId);
        messageSender.sendLossamSignupURL(lossamSignUpURL);
    }

    public void validateNickName(final String nickname) {
        if (memberReader.existNickname(nickname)) {
            throw new DuplicatedNicknameException(
                    DUPLICATE_MEMBER_NICKNAME,
                    new ServerErrorInfo(null, "중복된 닉네임 입니다: " + nickname)
            );
        }
    }

}
