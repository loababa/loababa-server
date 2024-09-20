package com.loababa.api.auth.domain.member.impl.model;

import org.springframework.stereotype.Component;

@Component
public class LossamSignUpURLGenerator {

    private static final String LOSSAM_SIGN_UP_URL = "https://www.loababa.com/lossam/signup?key=%s";

    public String generate(final LossamSignUpKey lossamSignUpKey) {
        return LOSSAM_SIGN_UP_URL.formatted(lossamSignUpKey.value());
    }

}
