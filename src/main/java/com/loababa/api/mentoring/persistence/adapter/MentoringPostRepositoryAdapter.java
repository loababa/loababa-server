package com.loababa.api.mentoring.persistence.adapter;


import com.loababa.api.mentoring.domain.impl.model.MentoringPost;
import com.loababa.api.auth.infra.persistance.repository.MemberJpaRepository;
import com.loababa.api.mentoring.domain.impl.model.MentoringDetailForm;
import com.loababa.api.mentoring.domain.impl.model.MentoringPostListForms;
import com.loababa.api.mentoring.domain.impl.repository.MentoringPostReader;
import com.loababa.api.mentoring.domain.impl.repository.MentoringPostWriter;
import com.loababa.api.mentoring.persistence.entity.MentoringPostEntity;
import com.loababa.api.mentoring.persistence.entity.MentoringPostTopicEntity;
import com.loababa.api.mentoring.persistence.repository.MentoringPostJpaRepository;
import com.loababa.api.mentoring.persistence.repository.MentoringPostTopicEntityJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MentoringPostRepositoryAdapter implements MentoringPostWriter, MentoringPostReader {

    private final MentoringPostJpaRepository mentoringPostJpaRepository;
    private final MentoringPostTopicEntityJpaRepository mentoringPostTopicEntityJpaRepository;

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void save(MentoringPost mentoringPost, Long memberId) {
        final var mentoringPostEntity = new MentoringPostEntity(mentoringPost.title(), mentoringPost.contents(), memberId);
        final Long mentoringPostId = mentoringPostJpaRepository.save(mentoringPostEntity)
                .getId();

        var mentoringPostTopicEntities = mentoringPost.topics().stream()
                .map(topic -> new MentoringPostTopicEntity(topic, mentoringPostId))
                .toList();
        mentoringPostTopicEntityJpaRepository.saveAll(mentoringPostTopicEntities);
    }

    @Override
    public MentoringPostListForms getAllMentoringPostListForm(List<Long> allLossamId) {
        // 1. 멘토링 포스트 엔티티를 가져옵니다.
        List<MentoringPostEntity> mentoringPostEntities = mentoringPostJpaRepository.findAllByMemberIdIn(allLossamId);

        // 2. 멘토링 포스트 ID 목록 추출합니다.
        List<Long> mentoringPostIds = mentoringPostEntities.stream()
                .map(MentoringPostEntity::getId)
                .toList();

        // 3. 멘토링 포스트 토픽 엔티티를 가져옵니다.
        List<MentoringPostTopicEntity> topics = mentoringPostTopicEntityJpaRepository.findAllByMentoringPostIdIn(mentoringPostIds);

        // 4. 멘토링 포스트 ID를 키로 하는 맵을 생성합니다.
        Map<Long, List<String>> topicsMappedPostId = topics.stream()
                .collect(
                        Collectors.groupingBy(
                                MentoringPostTopicEntity::getMentoringPostId,
                                Collectors.mapping(MentoringPostTopicEntity::getTopic, Collectors.toList())
                        )
                );

        // 5. MentoringPostListForms 객체 생성 및 반환
        return new MentoringPostListForms(
                mentoringPostEntities.stream()
                        .map(postEntity -> {
                            List<String> topicList = topicsMappedPostId.getOrDefault(postEntity.getId(), List.of());

                            return new MentoringPostListForms.MentoringPostListForm(
                                    postEntity.getMemberId(),
                                    postEntity.getContents(),
                                    topicList
                            );
                        })
                        .toList()
        );
    }

    @Override
    public MentoringDetailForm readMentoringDetailForm(Long mentoringPostId) {
        var mentoringPostEntity = mentoringPostJpaRepository.findById(mentoringPostId)
                .orElseThrow(() -> new EntityNotFoundException("해당 멘토링 포스트 ID가 존재 하지 않습니다."));
        return new MentoringDetailForm(
                mentoringPostEntity.getTitle(),
                mentoringPostEntity.getContents()
        );
    }
}
