package com.loababa.api.auth.infra.persistance.dto;

import com.loababa.api.auth.domain.member.impl.model.LossamBasicInfo;
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


    public LossamBasicInfo toLossamBasicInfo() {
        return new LossamBasicInfo(
                id, nickname, profileImageUrl, highestLevel, getClassEngravings()
        );
    }

}
