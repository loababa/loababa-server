package com.loababa.api.common.infra;

import com.loababa.api.common.config.WebConfig;
import com.loababa.api.common.service.impl.MessageSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static com.loababa.api.common.exception.CommonClientExceptionInfo.DISCORD_COMMUNICATION_FAIL;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Profile("default")
@Component
public class SlackMessageSender implements MessageSender {

    private final String slackWebhookUrl;
    private final RestClient restClient;

    public SlackMessageSender(
            @Value("${slack.webhook-url}")
            String slackWebhookUrl,
            RestClient restClient
    ) {
        this.slackWebhookUrl = slackWebhookUrl;
        this.restClient = restClient;
    }

    @Override
    public void sendLossamSignupURL(String message) {
        send(message);
    }

    @Override
    public void sendErrorNotification(String message) {
        send(message);
    }

    @Override
    public void sendConsultingNotification(String contactNumber) {
        send(contactNumber);
    }

    private void send(String message) {
        restClient.post()
                .uri(slackWebhookUrl)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(new SlackMessage(message))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        WebConfig.getCommonExternalCommunicationExceptionHandler(DISCORD_COMMUNICATION_FAIL, message)
                )
                .toBodilessEntity();
    }

    private record SlackMessage(
            String text
    ) {

    }

}
