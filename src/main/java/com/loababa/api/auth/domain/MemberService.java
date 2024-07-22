package com.loababa.api.auth.domain;

import com.loababa.api.auth.domain.impl.JWTManager;
import com.loababa.api.auth.domain.impl.model.AuthToken;
import com.loababa.api.auth.domain.impl.model.LossamLostArkCharacterInfo;
import com.loababa.api.auth.domain.impl.model.LossamSignUpKeyGenerator;
import com.loababa.api.auth.domain.impl.model.LossamSignUpKeyValidator;
import com.loababa.api.auth.domain.impl.model.LossamSignUpURLGenerator;
import com.loababa.api.auth.domain.impl.model.MemberProfile;
import com.loababa.api.auth.domain.impl.repository.LostArkCharacterInfoWriter;
import com.loababa.api.auth.domain.impl.repository.MemberReader;
import com.loababa.api.auth.domain.impl.repository.MemberWriter;
import com.loababa.api.auth.exception.DuplicatedNicknameException;
import com.loababa.api.auth.ui.AuthCredential;
import com.loababa.api.common.exception.ServerExceptionInfo;
import com.loababa.api.common.service.impl.MessageSender;
import com.loababa.api.mentoring.domain.impl.repository.MentoringPostWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.loababa.api.auth.exception.MemberClientExceptionInfo.DUPLICATE_MEMBER_NICKNAME;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final LossamSignUpURLGenerator lossamSignUpURLGenerator;
    private final LossamSignUpKeyGenerator lossamSignUpKeyGenerator;
    private final LossamSignUpKeyValidator lossamSignUpKeyValidator;

    private final MessageSender messageSender;
    private final JWTManager jwtManager;

    private final MemberReader memberReader;
    private final MemberWriter memberWriter;
    private final MentoringPostWriter mentoringPostWriter;
    private final LostArkCharacterInfoWriter lostArkCharacterInfoWriter;

    public void generateLossamSignupURL() {
        final var lossamSignUpKey = lossamSignUpKeyGenerator.generate();
        final String lossamSignUpURL = lossamSignUpURLGenerator.generate(lossamSignUpKey);
        messageSender.sendLossamSignupURL(lossamSignUpURL);
    }

    public void validateNickName(final String nickname) {
        if (memberReader.existNickname(nickname)) {
            throw new DuplicatedNicknameException(
                    DUPLICATE_MEMBER_NICKNAME,
                    new ServerExceptionInfo("중복된 닉네임 입니다: " + nickname)
            );
        }
    }

    public AuthToken signupLossam(
            final String key,
            final Long oauthId,
            final MemberProfile memberProfile,
            final LossamLostArkCharacterInfo lossamLostArkCharacterInfo
    ) {
        lossamSignUpKeyValidator.validate(key);

        final Long memberId = memberWriter.save(memberProfile, oauthId);
        lostArkCharacterInfoWriter.save(lossamLostArkCharacterInfo, memberId);

        return jwtManager.generate(
                new AuthCredential(oauthId, memberId)
        );
    }

}
