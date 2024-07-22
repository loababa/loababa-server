package com.loababa.api.auth.domain.member.impl.model;

import org.instancio.Instancio;

import static org.instancio.Select.allStrings;
import static org.instancio.Select.field;

public class LossamLostArkCharacterInfoFixtures {

    public static LossamLostArkCharacterInfo newLossamLostArkCharacterInfo() {
        return Instancio.of(LossamLostArkCharacterInfo.class)
                .generate(field(LossamLostArkCharacterInfo::highestLevel), gen -> gen.ints().range(1, 9999))
                .set(
                        field(LossamLostArkCharacterInfo::classEngravings),
                        Instancio.ofList(String.class)
                                .size(2)
                                .filter(allStrings(), (String s) -> s.length() <= 3)
                                .create()
                )
                .create();
    }

}
