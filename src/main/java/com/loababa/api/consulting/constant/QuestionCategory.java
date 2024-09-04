package com.loababa.api.consulting.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionCategory {

    CHARACTER_DETAILS("상담 캐릭터명, 직업 각인, 아이템 레벨 (모코코의 로스트아크 캐릭터 정보)"),
    INQUIRY_DETAILS("상담받고 싶은 내용"),
    EXPERIENCE("로스타아크 경력"),
    CONTACT_NUMBER("연락처 정보"),
    ;

    private final String description;

}
