package com.loababa.api.auth.domain.member;

import com.loababa.api.auth.domain.auth.impl.model.token.JWTManager;
import com.loababa.api.auth.domain.member.impl.model.LossamSignUpKey;
import com.loababa.api.auth.domain.member.impl.model.LossamSignUpKeyGenerator;
import com.loababa.api.auth.domain.member.impl.model.LossamSignUpKeyValidator;
import com.loababa.api.auth.domain.member.impl.model.LossamSignUpURLGenerator;
import com.loababa.api.auth.domain.member.impl.model.MemberProfile;
import com.loababa.api.auth.domain.member.impl.model.MemberType;
import com.loababa.api.auth.domain.member.impl.model.ProfileImageUrlResolver;
import com.loababa.api.auth.domain.member.impl.repository.LostArkCharacterInfoWriter;
import com.loababa.api.auth.domain.member.impl.repository.MemberReader;
import com.loababa.api.auth.domain.member.impl.repository.MemberWriter;
import com.loababa.api.auth.exception.DuplicatedNicknameException;
import com.loababa.api.common.MockTestBase;
import com.loababa.api.common.model.MemberCredential;
import com.loababa.api.common.service.impl.MessageSender;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.loababa.api.auth.domain.auth.model.token.AuthTokenFixtures.newAuthToken;
import static com.loababa.api.auth.domain.member.impl.model.LossamLostArkCharacterInfoFixtures.newLossamLostArkCharacterInfo;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

class MemberServiceTest extends MockTestBase {

    @InjectMocks
    private MemberService memberService;

    @Mock
    LossamSignUpURLGenerator lossamSignUpURLGenerator;
    @Mock
    LossamSignUpKeyGenerator lossamSignUpKeyGenerator;
    @Mock
    LossamSignUpKeyValidator lossamSignUpKeyValidator;
    @Mock
    JWTManager jwtManager;
    @Mock
    MemberReader memberReader;
    @Mock
    MemberWriter memberWriter;
    @Mock
    LostArkCharacterInfoWriter lostArkCharacterInfoWriter;
    @Mock
    ProfileImageUrlResolver profileImageUrlResolver;
    @Mock
    MessageSender messageSender;

    @Test
    void 로쌤_회원가입_URL을_생성하고_URL을_전송할_수_있다() {
        // given
        var lossamSignUpKey = new LossamSignUpKey("key");
        given(lossamSignUpKeyGenerator.generate()).willReturn(lossamSignUpKey);

        String lossamSignUpUrl = "lossamSignUpUrl";
        given(lossamSignUpURLGenerator.generate(lossamSignUpKey)).willReturn(lossamSignUpUrl);

        // when
        memberService.generateLossamSignupURL();

        // then
        then(messageSender).should(times(1)).sendLossamSignupURL(lossamSignUpUrl);
    }

    @Nested
    class 닉네임_중복을_검증할_수_있다 {

        @Test
        void 중복되는_닉네임이_존재하지_않을_경우() {
            // given
            String nickname = "nickname";
            given(memberReader.existNickname(nickname)).willReturn(false);

            // when

            // then
            assertThatCode(() -> memberService.validateNickName(nickname))
                    .doesNotThrowAnyException();
        }

        @Test
        void 중복되는_닉네임이_존재할_경우() {
            // given
            String nickname = "nickname";
            given(memberReader.existNickname(nickname)).willReturn(true);

            // when

            // then
            assertThatThrownBy(() -> memberService.validateNickName(nickname))
                    .isInstanceOf(DuplicatedNicknameException.class);
        }
    }

    @Test
    void 로쌤_회원가입을_할_수_있다() {
        // given
        var key = "key";
        var oauthId = 1L;
        var nickname = "nickname";
        var providedProfileImageUrl = "s3Url/profile-images/image.png";
        var lossamLostArkCharacterInfo = newLossamLostArkCharacterInfo();

        var resolvedProfileImageUrl = "cloudfrontUrl/profile-images/image.png";
        given(profileImageUrlResolver.resolveOrDefaultProfileImageUrl(providedProfileImageUrl)).willReturn(resolvedProfileImageUrl);

        var lossamProfile = new MemberProfile(nickname, resolvedProfileImageUrl, MemberType.LOSSAM);

        Long memberId = 1L;
        given(memberWriter.save(lossamProfile, oauthId)).willReturn(memberId);

        MemberCredential memberCredential = new MemberCredential(oauthId, memberId);
        given(jwtManager.generate(memberCredential)).willReturn(newAuthToken());

        // when
        memberService.signupLossam(key, oauthId, nickname, providedProfileImageUrl, lossamLostArkCharacterInfo);

        // then
        then(lossamSignUpKeyValidator).should(times(1)).validate(key);
        then(lostArkCharacterInfoWriter).should(times(1)).save(lossamLostArkCharacterInfo, memberId);
    }

}
