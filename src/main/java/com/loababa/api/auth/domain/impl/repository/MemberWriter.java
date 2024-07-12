package com.loababa.api.auth.domain.impl.repository;

import com.loababa.api.auth.domain.impl.model.MemberProfile;

public interface MemberWriter {

    Long save(MemberProfile memberProfile, Long oauthId);

}
