package com.loababa.api.auth.config;

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
public class CacheConfig {

    @Bean
    public Cache refreshTokenCache(
            @Value("${jwt.refresh-token-expiration-time-in-sec}")
            int refreshTokenExpirationTimeInSec
    ) {
        return new CaffeineCache(
                "refreshTokenCache",
                Caffeine.newBuilder()
                        .expireAfterWrite(refreshTokenExpirationTimeInSec, TimeUnit.SECONDS)
                        .initialCapacity(256)
                        .build()
        );
    }

}
