package com.loababa.api.auth.domain.member.impl.model;

import java.util.List;


public record LossamBasicInfos(
        List<LossamBasicInfo> lossamBasicInfos
) {

    public List<Long> getAllLossamId() {
        return lossamBasicInfos.stream()
                .map(LossamBasicInfo::id)
                .toList();
    }

}

