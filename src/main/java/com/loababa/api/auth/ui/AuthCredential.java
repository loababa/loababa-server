package com.loababa.api.auth.ui;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public record AuthCredential(
        @NonNull Long oauthUserId,
        @Nullable Long memberId
) {
}
