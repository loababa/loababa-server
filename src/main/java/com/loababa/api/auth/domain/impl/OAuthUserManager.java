package com.loababa.api.auth.domain.impl;

import com.loababa.api.auth.domain.impl.model.OAuthUser;
import com.loababa.api.auth.domain.impl.repository.OAuthUserReader;
import com.loababa.api.auth.domain.impl.repository.OAuthUserWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthUserManager {

    private final OAuthUserWriter oAuthUserWriter;
    private final OAuthUserReader oAuthUserReader;

    public void saveOAuthUserIfNotExists(OAuthUser oAuthUser) {
        if (!oAuthUserReader.isRegisteredAuthUser(oAuthUser)) {
            oAuthUserWriter.save(oAuthUser);
        }
    }

}
