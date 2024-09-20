package com.loababa.api.auth.domain.member.impl.model;

import java.util.List;

public record LossamBasicInfo(
        Long id,
        String nickname,
        String profileImageUrl,
        int highestLevel,
        List<String> classEngravings
) {
}
