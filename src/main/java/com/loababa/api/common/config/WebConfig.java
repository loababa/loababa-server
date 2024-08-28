package com.loababa.api.common.config;

import com.loababa.api.auth.exception.ExternalCommunicationException;
import com.loababa.api.auth.ui.resolver.LossamCredentialResolver;
import com.loababa.api.auth.ui.resolver.MemberCredentialResolver;
import com.loababa.api.common.exception.ClientExceptionInfo;
import com.loababa.api.common.exception.ServerExceptionInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String COMMUNICATION_ERROR_SERVER_MESSAGE = """
            외부 통신 통신 오류
            request header: {0}
            request body: {1}
            ---
            response header: {2}
            response body: {3}
            ---
            """;

    private final MemberCredentialResolver memberCredentialResolver;
    private final LossamCredentialResolver lossamCredentialResolver;
    private final String domain;

    public WebConfig(
            MemberCredentialResolver memberCredentialResolver,
            LossamCredentialResolver lossamCredentialResolver,
            @Value("domain") String domain
    ) {
        this.memberCredentialResolver = memberCredentialResolver;
        this.lossamCredentialResolver = lossamCredentialResolver;
        this.domain = domain;
    }

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

    public static RestClient.ResponseSpec.ErrorHandler getCommonExternalCommunicationExceptionHandler(ClientExceptionInfo clientExceptionInfo, String requestBody) {
        return (request, response) -> {
            throw new ExternalCommunicationException(
                    clientExceptionInfo,
                    new ServerExceptionInfo(
                            MessageFormat.format(
                                    COMMUNICATION_ERROR_SERVER_MESSAGE,
                                    request.getHeaders(), requestBody,
                                    response.getHeaders(), convertInputStreamToString(response.getBody())
                            )
                    )
            );
        };
    }

    private static String convertInputStreamToString(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new ClassCastException(e.getMessage());
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberCredentialResolver);
        resolvers.add(lossamCredentialResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", domain)
                .allowedMethods("*")
                .allowCredentials(true);
    }

}
