package com.loababa.api.consulting.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class ConsultingCacheConfig {

    @Bean
    public Cache consultingCache() {
        return new CaffeineCache(
                "consultingCache",
                Caffeine.newBuilder()
                        .initialCapacity(256)
                        .build()
        );
    }

}
