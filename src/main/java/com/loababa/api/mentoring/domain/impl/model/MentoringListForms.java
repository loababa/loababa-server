package com.loababa.api.mentoring.domain.impl.model;

import java.util.List;

public record MentoringListForms(
        List<MentoringListForm> mentoringListForm
) {

    public record MentoringListForm(
            Long mentoringPostId,

            Long lossamId,
            String profileImageUrl,
            String nickname,
            int highestLevel,
            List<String> classEngravings,

            String mentoringTitle,
            List<String> mentoringTopics
    ) {
    }

}

