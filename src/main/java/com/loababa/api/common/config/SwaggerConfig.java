package com.loababa.api.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(info())
                .components(components());
    }

    private Info info() {
        return new Info()
                .title("로아바바")
                .description("API");
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes(AUTHORIZATION, securityScheme());
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .scheme("Bearer")
                .bearerFormat("JWT")
                .type(HTTP)
                .in(HEADER)
                .name(AUTHORIZATION);
    }

}
