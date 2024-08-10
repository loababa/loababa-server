package com.loababa.api.auth.domain.member.impl.model;

public enum MemberType {
    LOSSAM,
    MOKOKO;

    public boolean isMokoko() {
        return this == MOKOKO;
    }

}
