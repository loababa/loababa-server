package com.loababa.api.consulting.persistence.repository;

import com.loababa.api.consulting.persistence.entity.ConsultingPostTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultingPostTopicEntityJpaRepository extends JpaRepository<ConsultingPostTopicEntity, Long> {

    List<ConsultingPostTopicEntity> findByConsultingPostId(Long consultingPostId);

    List<ConsultingPostTopicEntity> findAllByConsultingPostIdIn(List<Long> consultingPostIds);

}
