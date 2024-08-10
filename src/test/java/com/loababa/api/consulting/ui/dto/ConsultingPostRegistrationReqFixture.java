package com.loababa.api.consulting.ui.dto;

import org.instancio.Instancio;

import static org.instancio.Select.allStrings;
import static org.instancio.Select.field;

public class ConsultingPostRegistrationReqFixture {

    public static ConsultingRegistrationReq newConsultingPostRegistrationReq() {
        return Instancio.of(ConsultingRegistrationReq.class)
                .generate(field(ConsultingRegistrationReq::title), gen -> gen.string().minLength(1).maxLength(200))
                .generate(field(ConsultingRegistrationReq::contents), gen -> gen.string().minLength(1).maxLength(500))
                .set(
                        field(ConsultingRegistrationReq::topics),
                        Instancio.ofList(String.class)
                                .size(6)
                                .filter(allStrings(), (String s) -> s.length() <= 6)
                                .create()
                )
                .create();
    }

}
