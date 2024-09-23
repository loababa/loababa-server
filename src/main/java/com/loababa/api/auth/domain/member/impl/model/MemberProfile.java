package com.loababa.api.auth.domain.member.impl.model;

public record MemberProfile(
        String nickname,
        String profileImageUrl,
        MemberType memberType
) {

}
