package com.loababa.api.auth.domain.member.impl.repository;

import com.loababa.api.auth.domain.member.impl.model.LossamLostArkCharacterInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LostArkCharacterInfoWriter {

    void save(LossamLostArkCharacterInfo lossamLostArkCharacterInfo, Long memberId);

}
