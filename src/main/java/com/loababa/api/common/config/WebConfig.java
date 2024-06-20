package com.loababa.api.common.config;

import com.loababa.api.auth.exception.ExternalCommunicationException;
import com.loababa.api.common.exception.ClientErrorInfo;
import com.loababa.api.common.exception.ServerErrorInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.stream.Collectors;

@Configuration
public class WebConfig {

    private static final String COMMUNICATION_ERROR_SERVER_MESSAGE = """
            외부 통신 통신 오류
            request header: {0}
            request body: {1}
            ---
            response header: {2}
            response body: {3}
            ---
            """;

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

    public static RestClient.ResponseSpec.ErrorHandler getCommonExternalCommunicationExceptionHandler(ClientErrorInfo clientErrorInfo, String requestBody) {
        return (request, response) -> {
            throw new ExternalCommunicationException(
                    clientErrorInfo,
                    new ServerErrorInfo(
                            null,
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

}
