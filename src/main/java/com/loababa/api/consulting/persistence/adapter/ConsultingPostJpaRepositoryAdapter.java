package com.loababa.api.consulting.persistence.adapter;


import com.loababa.api.consulting.domain.impl.model.ConsultingDetailForm;
import com.loababa.api.consulting.domain.impl.model.ConsultingPost;
import com.loababa.api.consulting.domain.impl.model.ConsultingPostListForms;
import com.loababa.api.consulting.domain.impl.repository.ConsultingPostReader;
import com.loababa.api.consulting.domain.impl.repository.ConsultingPostWriter;
import com.loababa.api.consulting.persistence.entity.ConsultingPostEntity;
import com.loababa.api.consulting.persistence.entity.ConsultingPostTopicEntity;
import com.loababa.api.consulting.persistence.repository.ConsultingPostJpaRepository;
import com.loababa.api.consulting.persistence.repository.ConsultingPostTopicEntityJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConsultingPostJpaRepositoryAdapter implements ConsultingPostWriter, ConsultingPostReader {

    private final ConsultingPostJpaRepository consultingPostJpaRepository;
    private final ConsultingPostTopicEntityJpaRepository consultingPostTopicEntityJpaRepository;

    @Override
    public void save(Long memberId, ConsultingPost consultingPost) {
        final var consultingPostEntity = new ConsultingPostEntity(consultingPost.title(), consultingPost.contents(), memberId);
        final Long consultingPostId = consultingPostJpaRepository.save(consultingPostEntity)
                .getId();

        var consultingPostTopicEntities = consultingPost.topics().stream()
                .map(topic -> new ConsultingPostTopicEntity(topic, consultingPostId))
                .toList();
        consultingPostTopicEntityJpaRepository.saveAll(consultingPostTopicEntities);
    }

    @Override
    public ConsultingPostListForms getAllConsultingPostListForm(List<Long> allLossamId) {
        // 1. 상담 포스트 엔티티를 가져옵니다.
        List<ConsultingPostEntity> consultingPostEntities = consultingPostJpaRepository.findAllByMemberIdIn(allLossamId);

        // 2. 상담 포스트 ID 목록 추출합니다.
        List<Long> consultingPostIds = consultingPostEntities.stream()
                .map(ConsultingPostEntity::getId)
                .toList();

        // 3. 상담 포스트 토픽 엔티티를 가져옵니다.
        List<ConsultingPostTopicEntity> topics = consultingPostTopicEntityJpaRepository.findAllByConsultingPostIdIn(consultingPostIds);

        // 4. 상담 포스트 ID를 키로 하는 맵을 생성합니다.
        Map<Long, List<String>> topicsMappedPostId = topics.stream()
                .collect(
                        Collectors.groupingBy(
                                ConsultingPostTopicEntity::getConsultingPostId,
                                Collectors.mapping(ConsultingPostTopicEntity::getTopic, Collectors.toList())
                        )
                );

        // 5. ConsultingPostListForms 객체 생성 및 반환
        return new ConsultingPostListForms(
                consultingPostEntities.stream()
                        .map(postEntity -> {
                            List<String> topicList = topicsMappedPostId.getOrDefault(postEntity.getId(), List.of());

                            return new ConsultingPostListForms.ConsultingPostListForm(
                                    postEntity.getMemberId(),
                                    postEntity.getContents(),
                                    topicList
                            );
                        })
                        .toList()
        );
    }

    @Override
    public ConsultingDetailForm readConsultingDetailForm(Long consultingPostId) {
        var consultingPostEntity = consultingPostJpaRepository.findById(consultingPostId)
                .orElseThrow(() -> new EntityNotFoundException("해당 상담 포스트 ID가 존재 하지 않습니다."));
        return new ConsultingDetailForm(
                consultingPostEntity.getTitle(),
                consultingPostEntity.getContents()
        );
    }
}
