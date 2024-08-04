package com.loababa.api.common.model;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Hidden
public record AuthCredential(
        @NonNull Long oauthUserId,
        @Nullable Long memberId
) {
}
