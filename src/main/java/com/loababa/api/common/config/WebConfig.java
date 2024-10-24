package com.loababa.api.common.config;

import com.loababa.api.auth.exception.ExternalCommunicationException;
import com.loababa.api.auth.ui.resolver.LossamCredentialResolver;
import com.loababa.api.auth.ui.resolver.MemberCredentialResolver;
import com.loababa.api.common.exception.ClientExceptionInfo;
import com.loababa.api.common.exception.ServerExceptionInfo;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClient;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
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

    public static final String FRONT_DOMAIN = "www.loababa.com";
    public static final String BACK_DOMAIN = "api.loababa.com";
    public static final String CDN_DOMAIN = "cdn.loababa.com";

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

    public WebConfig(
            MemberCredentialResolver memberCredentialResolver,
            LossamCredentialResolver lossamCredentialResolver
    ) {
        this.memberCredentialResolver = memberCredentialResolver;
        this.lossamCredentialResolver = lossamCredentialResolver;
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

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>(new ShallowEtagHeaderFilter());
        filterRegistrationBean.addUrlPatterns("/api/v1/consulting/posts");
        return filterRegistrationBean;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberCredentialResolver);
        resolvers.add(lossamCredentialResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173",
                        "https://" + FRONT_DOMAIN,
                        "https://" + BACK_DOMAIN
                )
                .allowedMethods(
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.HEAD.name()
                )
                .allowCredentials(true);
    }
}
