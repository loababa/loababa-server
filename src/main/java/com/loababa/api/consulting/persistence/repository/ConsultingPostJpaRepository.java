package com.loababa.api.consulting.persistence.repository;

import com.loababa.api.consulting.persistence.entity.ConsultingPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultingPostJpaRepository extends JpaRepository<ConsultingPostEntity, Long> {

    List<ConsultingPostEntity> findAllByMemberIdIn(List<Long> memberIds);

}
