package com.loababa.api.common.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationRegex {

    public static final String ISO_LOCAL_TIME = "^([01]\\d|2[0-3]):([0-5]\\d)$";

}
