package com.loababa.api.mentoring.persistence.adapter;


import com.loababa.api.auth.domain.impl.model.MentoringPost;
import com.loababa.api.mentoring.domain.impl.repository.MentoringPostWriter;
import com.loababa.api.mentoring.persistence.entity.MentoringPostEntity;
import com.loababa.api.mentoring.persistence.entity.MentoringPostTopicEntity;
import com.loababa.api.mentoring.persistence.repository.MentoringPostJpaRepository;
import com.loababa.api.mentoring.persistence.repository.MentoringPostTopicEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MentoringPostRepositoryAdapter implements MentoringPostWriter {

    private final MentoringPostJpaRepository mentoringPostJpaRepository;
    private final MentoringPostTopicEntityJpaRepository mentoringPostTopicEntityJpaRepository;


    @Override
    public void save(MentoringPost mentoringPost, Long memberId) {
        final var mentoringPostEntity = new MentoringPostEntity(mentoringPost.description(), mentoringPost.selfIntroduce(), memberId);
        final Long mentoringPostId = mentoringPostJpaRepository.save(mentoringPostEntity)
                .getId();

        var mentoringPostTopicEntities = mentoringPost.topics().stream()
                .map(topic -> new MentoringPostTopicEntity(topic, mentoringPostId))
                .toList();
        mentoringPostTopicEntityJpaRepository.saveAll(mentoringPostTopicEntities);
    }
}
