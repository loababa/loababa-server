package com.loababa.api.auth.domain.member.impl.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class LossamSignUpURLGeneratorTest {

    private final LossamSignUpURLGenerator lossamSignUpURLGenerator = new LossamSignUpURLGenerator();

    @Test
    void 로쌤_회원가입_URL을_생성할_수_있다() {
        // given
        var lossamSignUpKey = new LossamSignUpKey("key");

        // when
        String lossamSignupURL = lossamSignUpURLGenerator.generate(lossamSignUpKey);
        System.out.println(lossamSignupURL);

        // then
        assertThat(lossamSignupURL).isEqualTo(lossamSignupURL.formatted(lossamSignUpKey.value()));
    }

}
