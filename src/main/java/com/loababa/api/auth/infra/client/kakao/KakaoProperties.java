package com.loababa.api.auth.infra.client.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@ConfigurationProperties("oauth.kakao")
public record KakaoProperties(
        String clientId,
        String redirectUri,
        String clientSecret,
        String responseType
) {

    public MultiValueMap<String, String> toKakaoTokenRequestBody(String code) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", clientId);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("code", code);
        requestBody.add("client_secret", clientSecret);
        return requestBody;
    }

}
