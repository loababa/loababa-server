package com.loababa.api.consulting.domain.impl.repository;

import com.loababa.api.consulting.domain.impl.model.ConsultingPost;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ConsultingPostWriter {

    void save(Long memberId, ConsultingPost consultingPost);

}
