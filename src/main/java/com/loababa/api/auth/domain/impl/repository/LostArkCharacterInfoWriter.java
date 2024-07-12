package com.loababa.api.auth.domain.impl.repository;

import com.loababa.api.auth.domain.impl.model.LossamLostArkCharacterInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LostArkCharacterInfoWriter {

    void save(LossamLostArkCharacterInfo lossamLostArkCharacterInfo, Long memberId);

}
