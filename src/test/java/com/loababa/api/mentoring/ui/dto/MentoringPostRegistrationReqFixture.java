package com.loababa.api.mentoring.ui.dto;

import org.instancio.Instancio;

import static org.instancio.Select.allStrings;
import static org.instancio.Select.field;

public class MentoringPostRegistrationReqFixture {

    public static MentoringPostRegistrationReq newMentoringPostRegistrationReq() {
        return Instancio.of(MentoringPostRegistrationReq.class)
                .generate(field(MentoringPostRegistrationReq::title), gen -> gen.string().minLength(1).maxLength(200))
                .generate(field(MentoringPostRegistrationReq::contents), gen -> gen.string().minLength(1).maxLength(500))
                .set(
                        field(MentoringPostRegistrationReq::topics),
                        Instancio.ofList(String.class)
                                .size(6)
                                .filter(allStrings(), (String s) -> s.length() <= 6)
                                .create()
                )
                .create();
    }

}
