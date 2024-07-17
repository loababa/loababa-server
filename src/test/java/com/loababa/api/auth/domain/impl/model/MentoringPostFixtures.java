package com.loababa.api.auth.domain.impl.model;

import org.instancio.Instancio;

import static org.instancio.Select.allStrings;
import static org.instancio.Select.field;

public class MentoringPostFixtures {

    public static MentoringPost newMentoringPost() {
        return Instancio.of(MentoringPost.class)
                .generate(field(MentoringPost::description), gen -> gen.string().minLength(1).maxLength(200))
                .generate(field(MentoringPost::selfIntroduce), gen -> gen.string().minLength(1).maxLength(500))
                .set(
                        field(MentoringPost::topics),
                        Instancio.ofList(String.class)
                                .size(6)
                                .filter(allStrings(), (String s) -> s.length() <= 6)
                                .create()
                )
                .create();
    }

}
