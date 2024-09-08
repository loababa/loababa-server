package com.loababa.api.common.model;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public record LossamCredential(
        Long oauthUserId,
        Long lossamId
) {
}
