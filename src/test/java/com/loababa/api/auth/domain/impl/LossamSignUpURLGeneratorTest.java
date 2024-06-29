package com.loababa.api.auth.domain.impl;

import com.loababa.api.auth.domain.impl.model.LossamSignUpURLGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class LossamSignUpURLGeneratorTest {

    private LossamSignUpURLGenerator lossamSignUpURLGenerator;

    @BeforeEach
    void setUp() {
        String lossamSignUpURL = "lossamSignupURL?key=%s";
        lossamSignUpURLGenerator = new LossamSignUpURLGenerator(lossamSignUpURL);
    }

    @Test
    void 로쌤_회원가입_URL을_생성할_수_있다() {
        // given
        String nanoId = "nanoId";

        // when
        String lossamSignupURL = lossamSignUpURLGenerator.generate(nanoId);

        // then
        assertThat(lossamSignupURL).isEqualTo(lossamSignupURL.formatted(nanoId));
    }

}
