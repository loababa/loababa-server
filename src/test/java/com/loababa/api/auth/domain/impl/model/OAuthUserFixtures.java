package com.loababa.api.auth.domain.impl.model;

import org.instancio.Instancio;

import java.util.List;
import java.util.stream.Stream;

import static org.instancio.Select.field;

public final class OAuthUserFixtures {

    private OAuthUserFixtures() {
    }

    public static OAuthUser newOAuthUser() {
        return Instancio.create(OAuthUser.class);
    }

    public static List<OAuthUser> newOAuthUsers() {
        return Stream.of(OAuthPlatform.values())
                .map(oAuthPlatform -> Instancio.of(OAuthUser.class)
                        .set(field(OAuthUser::oAuthPlatform), oAuthPlatform)
                        .create())
                .toList();
    }

}
