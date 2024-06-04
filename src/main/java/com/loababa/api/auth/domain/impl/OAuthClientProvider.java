package com.loababa.api.auth.domain.impl;

import com.loababa.api.auth.domain.impl.model.OAuthPlatform;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Component
public class OAuthClientProvider {

    private final Map<OAuthPlatform, OAuthClient> platformToClient = new EnumMap<>(OAuthPlatform.class);

    public OAuthClientProvider(Set<OAuthClient> oAuthClients) {
        oAuthClients.forEach(
                oAuthClient -> platformToClient.put(oAuthClient.getOAuthPlatform(), oAuthClient)
        );
    }

    public OAuthClient getOAuthClient(OAuthPlatform oAuthPlatform) {
        return platformToClient.get(oAuthPlatform);
    }

}
