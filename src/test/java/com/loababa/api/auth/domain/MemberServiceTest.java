package com.loababa.api.auth.domain;

import com.loababa.api.auth.domain.impl.model.LossamSignUpURLGenerator;
import com.loababa.api.auth.domain.impl.repository.NanoIdGenerator;
import com.loababa.api.auth.exception.DuplicatedNicknameException;
import com.loababa.api.common.MockTestBase;
import com.loababa.api.common.service.impl.MessageSender;
import com.loababa.api.auth.domain.impl.repository.MemberReader;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

class MemberServiceTest extends MockTestBase {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberReader memberReader;

    @Mock
    private NanoIdGenerator nanoIdGenerator;

    @Mock
    private LossamSignUpURLGenerator lossamSignUpUrlGenerator;

    @Mock
    private MessageSender messageSender;

    @Test
    void 로쌤_회원가입_URL을_생성하고_URL을_전송할_수_있다() {
        // given
        String nanoId = "nanoId";
        given(nanoIdGenerator.generate()).willReturn(nanoId);

        String lossamSignUpUrl = "lossamSignUpUrl";
        given(lossamSignUpUrlGenerator.generate(nanoId)).willReturn(lossamSignUpUrl);

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

}
