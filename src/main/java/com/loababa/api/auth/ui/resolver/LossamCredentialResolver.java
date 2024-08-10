package com.loababa.api.auth.ui.resolver;

import com.loababa.api.auth.domain.member.impl.model.MemberType;
import com.loababa.api.auth.domain.member.impl.repository.MemberReader;
import com.loababa.api.auth.exception.MemberClientExceptionInfo;
import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import com.loababa.api.common.model.LossamCredential;
import com.loababa.api.common.model.MemberCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LossamCredentialResolver implements HandlerMethodArgumentResolver {

    private final MemberCredentialResolver memberCredentialResolver;
    private final MemberReader memberReader;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(LossamCredential.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        var memberCredential = (MemberCredential) memberCredentialResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        assert memberCredential != null;
        Long memberId = memberCredential.memberId();

        assert memberId != null;
        MemberType memberType = memberReader.readMemberType(memberId);
        if (memberType.isMokoko()) {
            throw new LoababaBadRequestException(
                    MemberClientExceptionInfo.UNAUTHORIZED,
                    new ServerExceptionInfo("권한이 없습니다. Member Id: " + memberId + ", " + "MemberType: " + memberType)
            );
        }
        return memberCredential.toLossamCredential();
    }
}
