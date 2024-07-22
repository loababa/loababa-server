package com.loababa.api.auth.ui.dto;

import org.instancio.Instancio;

import static org.instancio.Select.allStrings;
import static org.instancio.Select.field;

public final class LossamSignUpReqFixtures {
    private LossamSignUpReqFixtures() {
    }

    public static LossamSignUpReq newLossamSignUpReq() {
        return Instancio.of(LossamSignUpReq.class)
                .generate(field(LossamSignUpReq::nickname), gen -> gen.string().minLength(2).maxLength(8))
                .generate(field(LossamSignUpReq::profileImageURL), gen -> gen.net().url().asString())
                .generate(field(LossamSignUpReq::highestLevel), gen -> gen.ints().range(1, 9999))
                .set(
                        field(LossamSignUpReq::classEngravings),
                        Instancio.ofList(String.class)
                                .size(2)
                                .filter(allStrings(), (String s) -> s.length() <= 3)
                                .create()
                )
                .create();
    }

}
