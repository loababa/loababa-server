package com.loababa.api.consulting.domain.impl.model;

import java.util.List;

public record ConsultingPostDetailForm(
        String title,
        String contents,
        List<String> topics
) {
}
