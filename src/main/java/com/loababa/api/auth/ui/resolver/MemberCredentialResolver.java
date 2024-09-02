package com.loababa.api.auth.ui.resolver;

import com.loababa.api.auth.domain.auth.impl.model.token.JWTManager;
import com.loababa.api.auth.exception.InvalidTokenException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import com.loababa.api.common.model.MemberCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.loababa.api.auth.domain.auth.impl.model.token.JWTProperties.TOKEN_PREFIX;
import static com.loababa.api.auth.exception.AuthClientExceptionInfo.INVALID_TOKEN;
import static com.loababa.api.auth.exception.AuthClientExceptionInfo.LOGIN_REQUIRED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class MemberCredentialResolver implements HandlerMethodArgumentResolver {

    private final JWTManager jwtManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberCredential.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorizationHeader = webRequest.getHeader(AUTHORIZATION);
        if (authorizationHeader == null) {
            throw new InvalidTokenException(
                    LOGIN_REQUIRED,
                    new ServerExceptionInfo("토큰이 존재하지 않습니다.")
            );
        }
        if (!authorizationHeader.startsWith(TOKEN_PREFIX)) {
            throw new InvalidTokenException(
                    INVALID_TOKEN,
                    new ServerExceptionInfo("잘못된 토큰 입니다. token: " + authorizationHeader)
            );
        }
        return jwtManager.extractClaims(authorizationHeader);
    }
}
