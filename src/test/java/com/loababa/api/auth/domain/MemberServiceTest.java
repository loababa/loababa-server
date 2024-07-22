package com.loababa.api.auth.domain;

import com.loababa.api.auth.domain.impl.JWTManager;
import com.loababa.api.auth.domain.impl.model.LossamSignUpKey;
import com.loababa.api.auth.domain.impl.model.LossamSignUpKeyGenerator;
import com.loababa.api.auth.domain.impl.model.LossamSignUpKeyValidator;
import com.loababa.api.auth.domain.impl.model.LossamSignUpURLGenerator;
import com.loababa.api.auth.domain.impl.repository.LostArkCharacterInfoWriter;
import com.loababa.api.auth.domain.impl.repository.MemberReader;
import com.loababa.api.auth.domain.impl.repository.MemberWriter;
import com.loababa.api.auth.exception.DuplicatedNicknameException;
import com.loababa.api.auth.ui.AuthCredential;
import com.loababa.api.common.MockTestBase;
import com.loababa.api.common.service.impl.MessageSender;
import com.loababa.api.mentoring.domain.impl.repository.MentoringPostWriter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.loababa.api.auth.domain.impl.model.AuthTokenFixtures.newAuthToken;
import static com.loababa.api.auth.domain.impl.model.LossamLostArkCharacterInfoFixtures.newLossamLostArkCharacterInfo;
import static com.loababa.api.auth.domain.impl.model.MemberProfileFixtures.newLossamProfile;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

class MemberServiceTest extends MockTestBase {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private LossamSignUpURLGenerator lossamSignUpURLGenerator;
    @Mock
    private LossamSignUpKeyGenerator lossamSignUpKeyGenerator;
    @Mock
    private LossamSignUpKeyValidator lossamSignUpKeyValidator;

    @Mock
    private MessageSender messageSender;
    @Mock
    private JWTManager jwtManager;

    @Mock
    private MemberReader memberReader;
    @Mock
    private MemberWriter memberWriter;
    @Mock
    private MentoringPostWriter mentoringPostWriter;
    @Mock
    private LostArkCharacterInfoWriter lostArkCharacterInfoWriter;

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
        String key = "key";
        Long oauthId = 1L;
        var lossamProfile = newLossamProfile();
        var lossamLostArkCharacterInfo = newLossamLostArkCharacterInfo();

        Long memberId = 1L;
        given(memberWriter.save(lossamProfile, oauthId)).willReturn(memberId);

        AuthCredential authCredential = new AuthCredential(oauthId, memberId);
        given(jwtManager.generate(authCredential)).willReturn(newAuthToken());

        // when
        memberService.signupLossam(key, oauthId, lossamProfile, lossamLostArkCharacterInfo);

        // then
        then(lossamSignUpKeyValidator).should(times(1)).validate(key);
        then(lostArkCharacterInfoWriter).should(times(1)).save(lossamLostArkCharacterInfo, memberId);

    }

}
