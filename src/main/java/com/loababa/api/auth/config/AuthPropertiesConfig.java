package com.loababa.api.auth.config;

import com.loababa.api.auth.domain.impl.model.JWTProperties;
import com.loababa.api.auth.infra.client.kakao.KakaoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JWTProperties.class, KakaoProperties.class})
public class AuthPropertiesConfig {

}
