package com.loababa.api.auth.domain.impl.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OAuthPlatform {
    KAKAO,
    GOOGLE,
    ;

    public static OAuthPlatform from(String platform) {
        return OAuthPlatform.valueOf(platform.toUpperCase());
    }

}
