package com.loababa.api.consulting.domain.impl.repository;

import com.loababa.api.consulting.domain.impl.model.ConsultingPost;

public interface ConsultingPostWriter {

    void save(Long memberId, ConsultingPost consultingPost);

}
