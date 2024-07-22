package com.loababa.api.auth.domain.member.impl.repository;

import com.loababa.api.auth.domain.member.impl.model.MemberProfile;

public interface MemberWriter {

    Long save(MemberProfile memberProfile, Long oauthId);

}
