package com.loababa.api.mentoring.domain.impl.model;

import java.util.List;

public record MentoringPost(
        String title,
        String contents,
        List<String> topics
) {
}
