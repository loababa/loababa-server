package com.loababa.api.common.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public record AuthCredential(
        @NonNull Long oauthUserId,
        @Nullable Long memberId
) {
}
