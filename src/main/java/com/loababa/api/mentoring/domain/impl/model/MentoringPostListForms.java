package com.loababa.api.mentoring.domain.impl.model;

import java.util.List;

public record MentoringPostListForms(
        List<MentoringPostListForm> mentoringPostListForms
) {

    public record MentoringPostListForm(
            Long lossamId,
            String mentoringTitle,
            List<String> mentoringTopics
    ) {

    }

}
