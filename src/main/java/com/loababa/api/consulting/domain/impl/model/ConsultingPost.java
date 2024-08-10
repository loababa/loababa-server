package com.loababa.api.consulting.domain.impl.model;

import java.util.List;

public record ConsultingPost(
        String title,
        String contents,
        List<String> topics
) {
}
