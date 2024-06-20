package com.loababa.api.member.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class MemberCacheConfig {

    @Bean
    Cache lossamSignUpKeyCache(
            @Value("${jwt.lossam-sign-up-key-expiration-time-in-sec}")
            int lossamSignUpKeyExpirationTimeInSec
    ) {
        return new CaffeineCache(
                "lossamSignUpKeyCache",
                Caffeine.newBuilder()
                        .expireAfterWrite(lossamSignUpKeyExpirationTimeInSec, TimeUnit.SECONDS)
                        .initialCapacity(8)
                        .build()
        );
    }

}
