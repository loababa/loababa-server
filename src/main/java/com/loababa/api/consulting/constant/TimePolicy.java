package com.loababa.api.consulting.constant;

import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Period;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public final class TimePolicy {

    public static final Period RESERVATION_AVAILABLE_PERIOD = Period.ofWeeks(2);

    public static final Duration RESERVATION_SLOT_INTERVAL = Duration.ofHours(1);

}
