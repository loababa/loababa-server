package com.loababa.api.auth.infra.persistance.adapter.member;

import com.loababa.api.auth.domain.impl.model.LossamLostArkCharacterInfo;
import com.loababa.api.auth.domain.impl.repository.LostArkCharacterInfoWriter;
import com.loababa.api.auth.infra.persistance.entity.LossamLostArkCharacterInfoJpaRepository;
import com.loababa.api.auth.infra.persistance.entity.LostArkCharacterInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LostArkCharacterInfoJpaRepositoryAdapter implements LostArkCharacterInfoWriter {

    private final LossamLostArkCharacterInfoJpaRepository lossamLostArkCharacterInfoJpaRepository;

    @Override
    public void save(LossamLostArkCharacterInfo lossamLostArkCharacterInfo, Long memberId) {
        String classEngravings = String.join(", ", lossamLostArkCharacterInfo.classEngravings());
        var lostArkCharacterInfoEntity = new LostArkCharacterInfoEntity(
                lossamLostArkCharacterInfo.highestLevel(),
                classEngravings,
                memberId
        );
        lossamLostArkCharacterInfoJpaRepository.save(lostArkCharacterInfoEntity);
    }

}
