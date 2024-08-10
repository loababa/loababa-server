package com.loababa.api.consulting.domain.impl.model;

import java.util.List;

public record ConsultingPostListForms(
        List<ConsultingPostListForm> consultingPostListForms
) {

    public record ConsultingPostListForm(
            Long lossamId,
            String consultingTitle,
            List<String> consultingTopics
    ) {

    }

}
