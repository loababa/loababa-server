package com.loababa.api.auth.domain.impl.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.stereotype.Component;

@Component
public class LossamSignUpNanoIdKeyGenerator implements LossamSignUpKeyGenerator {

    @Override
    public LossamSignUpKey generate() {
        return new LossamSignUpKey(NanoIdUtils.randomNanoId());
    }

}
