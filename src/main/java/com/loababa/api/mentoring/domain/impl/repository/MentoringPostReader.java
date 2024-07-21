package com.loababa.api.mentoring.domain.impl.repository;

import com.loababa.api.mentoring.domain.impl.model.MentoringPostListForms;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public interface MentoringPostReader {

    @Transactional(readOnly = true)
    MentoringPostListForms getAllMentoringPostListForm(List<Long> allLossamId);

}
