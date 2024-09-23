package com.loababa.api.auth.domain.member;

import com.loababa.api.auth.domain.auth.impl.model.token.AuthToken;
import com.loababa.api.auth.domain.auth.impl.model.token.JWTManager;
import com.loababa.api.auth.domain.member.impl.model.LossamLostArkCharacterInfo;
import com.loababa.api.auth.domain.member.impl.model.LossamSignUpKeyGenerator;
import com.loababa.api.auth.domain.member.impl.model.LossamSignUpKeyValidator;
import com.loababa.api.auth.domain.member.impl.model.LossamSignUpURLGenerator;
import com.loababa.api.auth.domain.member.impl.model.Member;
import com.loababa.api.auth.domain.member.impl.model.MemberProfile;
import com.loababa.api.auth.domain.member.impl.model.ProfileImageUrlResolver;
import com.loababa.api.auth.domain.member.impl.repository.LostArkCharacterInfoWriter;
import com.loababa.api.auth.domain.member.impl.repository.MemberReader;
import com.loababa.api.auth.domain.member.impl.repository.MemberWriter;
import com.loababa.api.auth.exception.DuplicatedNicknameException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import com.loababa.api.common.model.MemberCredential;
import com.loababa.api.common.service.impl.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.loababa.api.auth.domain.member.impl.model.MemberType.LOSSAM;
import static com.loababa.api.auth.exception.MemberClientExceptionInfo.DUPLICATE_MEMBER_NICKNAME;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final LossamSignUpURLGenerator lossamSignUpURLGenerator;
    private final LossamSignUpKeyGenerator lossamSignUpKeyGenerator;
    private final LossamSignUpKeyValidator lossamSignUpKeyValidator;

    private final JWTManager jwtManager;
    private final MemberReader memberReader;
    private final MemberWriter memberWriter;
    private final LostArkCharacterInfoWriter lostArkCharacterInfoWriter;
    private final ProfileImageUrlResolver profileImageUrlResolver;

    private final MessageSender messageSender;

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
            final String nickname,
            final String providedProfileImageUrl,
            final LossamLostArkCharacterInfo lossamLostArkCharacterInfo
    ) {
        lossamSignUpKeyValidator.validate(key);

        final String resolvedProfileImageUrl = profileImageUrlResolver.resolveOrDefaultProfileImageUrl(providedProfileImageUrl);
        final var lossamProfile = new MemberProfile(nickname, resolvedProfileImageUrl, LOSSAM);

        final Long memberId = memberWriter.save(lossamProfile, oauthId);
        lostArkCharacterInfoWriter.save(lossamLostArkCharacterInfo, memberId);

        return jwtManager.generate(
                new MemberCredential(oauthId, memberId)
        );
    }

    public Member getMember(Long memberId) {
        return memberReader.read(memberId);
    }

}
