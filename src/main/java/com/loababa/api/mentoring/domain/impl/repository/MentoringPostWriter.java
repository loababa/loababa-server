package com.loababa.api.mentoring.domain.impl.repository;

import com.loababa.api.auth.domain.impl.model.MentoringPost;

public interface MentoringPostWriter {

    void save(MentoringPost mentoringPost, Long memberId);

}
