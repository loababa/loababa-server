package com.loababa.api.auth.domain.member.impl.repository;

import com.loababa.api.auth.domain.member.impl.model.LossamLostArkCharacterInfo;

public interface LostArkCharacterInfoWriter {

    void save(LossamLostArkCharacterInfo lossamLostArkCharacterInfo, Long memberId);

}
