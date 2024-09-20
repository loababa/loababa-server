package com.loababa.api.auth.domain.member.impl.model;

import static com.loababa.api.common.config.WebConfig.CDN_DOMAIN;

public record MemberProfile(
        String nickname,
        String profileImageUrl,
        MemberType memberType
) {

    public MemberProfile(String nickname, String profileImageUrl, MemberType memberType) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl == null ? defaultProfileImageUrl(memberType) : profileImageUrl;
        this.memberType = memberType;
    }

    private String defaultProfileImageUrl(MemberType memberType) {
        String fileName = switch (memberType) {
            case MOKOKO -> "/mokoko.png";
            case LOSSAM -> "/lossam.png";
        };
        return CDN_DOMAIN + fileName;
    }
}
