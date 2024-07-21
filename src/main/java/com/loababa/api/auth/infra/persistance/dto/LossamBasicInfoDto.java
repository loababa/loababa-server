package com.loababa.api.auth.infra.persistance.dto;

import com.loababa.api.auth.domain.impl.model.LossamBasicInfos;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class LossamBasicInfoDto {

    private final Long id;
    private final String nickname;
    private final String profileImageUrl;
    private final Integer highestLevel;
    private final String classEngravings;


    public List<String> getClassEngravings() {
        return Arrays.asList(classEngravings.split(", "));
    }


    public LossamBasicInfos.LossamBasicInfo toLossamBasicInfo() {
        return new LossamBasicInfos.LossamBasicInfo(
                id, nickname, profileImageUrl, highestLevel, getClassEngravings()
        );
    }

}
