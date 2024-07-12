package com.loababa.api.auth.domain.impl.model;

import java.util.List;

public record MentoringPost(
        String description,
        String selfIntroduce,
        List<String> topics
) {
}
