package com.loababa.api.auth.domain.auth.impl.model.oauth;

import com.loababa.api.auth.domain.auth.impl.repository.OAuthUserReader;
import com.loababa.api.auth.domain.auth.impl.repository.OAuthUserWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthUserManager {

    private final OAuthUserWriter oAuthUserWriter;
    private final OAuthUserReader oAuthUserReader;

    public Long saveOAuthUserIfNotExists(OAuthUser oAuthUser) {
        if (!oAuthUserReader.isRegisteredAuthUser(oAuthUser)) {
            return oAuthUserWriter.save(oAuthUser);
        }
        return oAuthUserReader.getId(oAuthUser);
    }

}
