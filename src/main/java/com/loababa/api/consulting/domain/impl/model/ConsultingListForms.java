package com.loababa.api.consulting.domain.impl.model;

import java.util.List;

public record ConsultingListForms(
        List<ConsultingListForm> consultingListForm
) {

    public record ConsultingListForm(
            Long consultingPostId,

            Long lossamId,
            String profileImageUrl,
            String nickname,
            int highestLevel,
            List<String> classEngravings,

            String consultingTitle,
            List<String> consultingTopics
    ) {
    }

}

