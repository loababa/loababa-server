package com.loababa.api.auth.domain.member.impl.model;


import org.instancio.Instancio;

import static org.instancio.Select.field;

public final class MemberProfileFixtures {

    private MemberProfileFixtures() {
    }

    public static MemberProfile newLossamProfile() {
        return Instancio.of(MemberProfile.class)
                .set(field(MemberProfile::memberType), MemberType.LOSSAM)
                .create();
    }

}
