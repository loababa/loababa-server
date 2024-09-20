package com.loababa.api.auth.ui.dto;

import com.loababa.api.auth.domain.member.impl.model.Member;
import com.loababa.api.auth.domain.member.impl.model.MemberType;

public record MemberMyResponse(
        Long id,
        String nickname,
        String profileImageUrl,
        MemberType memberType
) {

    public static MemberMyResponse from(Member member) {
        var memberProfile = member.memberProfile();
        return new MemberMyResponse(
                member.id(),
                memberProfile.nickname(),
                memberProfile.profileImageUrl(),
                memberProfile.memberType()
        );
    }

}
