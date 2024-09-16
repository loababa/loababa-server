package com.loababa.api.auth.infra.client.kakao;

import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthClient;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthPlatform;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthToken;
import com.loababa.api.auth.domain.auth.impl.model.oauth.OAuthUserInfo;
import com.loababa.api.common.config.WebConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import static com.loababa.api.auth.domain.auth.impl.model.token.JWTProperties.TOKEN_PREFIX;
import static com.loababa.api.auth.exception.AuthClientExceptionInfo.KAKAO_COMMUNICATION_FAIL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    private final RestClient restClient;
    private final KakaoProperties kakaoProperties;

    @Override
    public OAuthToken fetchOAuthToken(String code, String redirectUri) {
        MultiValueMap<String, String> kakaoTokenRequestBody = kakaoProperties.toKakaoTokenRequestBody(code, redirectUri);

        KakaoOAuthToken kakaoOAuthToken = restClient
                .post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE)
                .body(kakaoTokenRequestBody)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        WebConfig.getCommonExternalCommunicationExceptionHandler(KAKAO_COMMUNICATION_FAIL, String.valueOf(kakaoTokenRequestBody))
                )
                .body(KakaoOAuthToken.class);
        assert kakaoOAuthToken != null;
        return new OAuthToken(kakaoOAuthToken.accessToken());
    }

    @Override
    public OAuthUserInfo fetchUserOAuthInfo(String accessToken) {
        KakaoOAuthAccessTokenInfo kakaoOAuthAccessTokenInfo = restClient
                .get()
                .uri("https://kapi.kakao.com/v1/user/access_token_info")
                .header(AUTHORIZATION, TOKEN_PREFIX + accessToken)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        WebConfig.getCommonExternalCommunicationExceptionHandler(KAKAO_COMMUNICATION_FAIL, null)
                )
                .body(KakaoOAuthAccessTokenInfo.class);

        assert kakaoOAuthAccessTokenInfo != null;
        return new OAuthUserInfo(kakaoOAuthAccessTokenInfo.id());
    }

    @Override
    public void invalidateOAuthToken(String accessToken) {
        restClient
                .post()
                .uri("https://kapi.kakao.com/v1/user/logout")
                .header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE)
                .header(AUTHORIZATION, TOKEN_PREFIX + accessToken)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        WebConfig.getCommonExternalCommunicationExceptionHandler(KAKAO_COMMUNICATION_FAIL, null)
                )
                .toBodilessEntity();
    }

    @Override
    public OAuthPlatform getOAuthPlatform() {
        return OAuthPlatform.KAKAO;
    }

}
