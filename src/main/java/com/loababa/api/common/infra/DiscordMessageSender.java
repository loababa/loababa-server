package com.loababa.api.common.infra;

import com.loababa.api.common.config.WebConfig;
import com.loababa.api.common.service.impl.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static com.loababa.api.common.exception.CommonClientExceptionInfo.DISCORD_COMMUNICATION_FAIL;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class DiscordMessageSender implements MessageSender {

    private final String lossamSignupWebhookUrl;
    private final String errorNotificationWebhookUrl;
    private final RestClient restClient;

    public DiscordMessageSender(
            @Value("${discord.lossam-signup-webhook-url}")
            String lossamSignupWebhookUrl,
            @Value("${discord.error-notification-webhook-url}")
            String errorNotificationWebhookUrl,
            RestClient restClient
    ) {
        this.lossamSignupWebhookUrl = lossamSignupWebhookUrl;
        this.errorNotificationWebhookUrl = errorNotificationWebhookUrl;
        this.restClient = restClient;
    }

    @Override
    public void sendLossamSignupURL(String message) {
        restClient.post()
                .uri(lossamSignupWebhookUrl)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(new DiscordMessage(message))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        WebConfig.getCommonExternalCommunicationExceptionHandler(DISCORD_COMMUNICATION_FAIL, message)
                )
                .toBodilessEntity();
    }

    @Override
    public void sendErrorNotification(String message) {
        restClient.post()
                .uri(errorNotificationWebhookUrl)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(new DiscordMessage(message))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        WebConfig.getCommonExternalCommunicationExceptionHandler(DISCORD_COMMUNICATION_FAIL, message)
                )
                .toBodilessEntity();
    }

    private record DiscordMessage(
            String content
    ) {
    }
}
