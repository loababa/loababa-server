package com.loababa.api.mentoring.persistence.repository;

import com.loababa.api.mentoring.persistence.entity.MentoringPostTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentoringPostTopicEntityJpaRepository extends JpaRepository<MentoringPostTopicEntity, Long> {

    List<MentoringPostTopicEntity> findAllByMentoringPostIdIn(List<Long> mentoringPostIds);

}
