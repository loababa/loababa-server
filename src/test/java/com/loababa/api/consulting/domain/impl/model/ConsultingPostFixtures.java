package com.loababa.api.consulting.domain.impl.model;

import org.instancio.Instancio;

import static org.instancio.Select.allStrings;
import static org.instancio.Select.field;

public class ConsultingPostFixtures {

    public static ConsultingPost newConsultingPost() {
        return Instancio.of(ConsultingPost.class)
                .generate(field(ConsultingPost::title), gen -> gen.string().minLength(1).maxLength(200))
                .generate(field(ConsultingPost::contents), gen -> gen.string().minLength(1).maxLength(500))
                .set(
                        field(ConsultingPost::topics),
                        Instancio.ofList(String.class)
                                .size(6)
                                .filter(allStrings(), (String s) -> s.length() <= 6)
                                .create()
                )
                .create();
    }

}
