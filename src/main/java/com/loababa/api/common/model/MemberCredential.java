package com.loababa.api.common.model;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public record MemberCredential(
        Long oauthUserId,
        Long memberId
) {

    public LossamCredential toLossamCredential() {
        return new LossamCredential(oauthUserId, memberId);
    }
}
