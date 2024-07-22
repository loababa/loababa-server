package com.loababa.api.mentoring.domain.impl.repository;

import com.loababa.api.mentoring.domain.impl.model.MentoringPost;

public interface MentoringPostWriter {

    void save(MentoringPost mentoringPost, Long memberId);

}
