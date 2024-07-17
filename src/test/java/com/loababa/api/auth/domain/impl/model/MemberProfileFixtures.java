package com.loababa.api.auth.domain.impl.model;


import org.instancio.Instancio;

import static org.instancio.Select.field;

public final class MemberProfileFixtures {

    private MemberProfileFixtures() {
    }

    public static MemberProfile newLossamProfile() {
        return Instancio.of(MemberProfile.class)
                .set(field(MemberProfile::memberType), MemberType.LOSSAM)
                .set(field(MemberProfile.ProfileImageURL::value), newProfileImageURL())
                .create();
    }

    public static MemberProfile.ProfileImageURL newProfileImageURL() {
        return Instancio.of(MemberProfile.ProfileImageURL.class)
                .generate(field(MemberProfile.ProfileImageURL::value), gen -> gen.net().url().asString())
                .create();
    }

}
