package com.loababa.api.auth.domain.impl.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LossamSignUpURLGenerator {

    private final String lossamSignUpURL;

    public LossamSignUpURLGenerator(
            @Value("${url.lossam-sign-up}")
            String lossamSignUpURL
    ) {
        this.lossamSignUpURL = lossamSignUpURL;
    }

    public String generate(String nanoId) {
        return lossamSignUpURL.formatted(nanoId);
    }

}
