package com.loababa.api.mentoring.domain.impl.repository;

import com.loababa.api.mentoring.domain.impl.model.MentoringDetailForm;
import com.loababa.api.mentoring.domain.impl.model.MentoringPostListForms;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public interface MentoringPostReader {

    MentoringPostListForms getAllMentoringPostListForm(List<Long> allLossamId);

    MentoringDetailForm readMentoringDetailForm(Long mentoringPostId);

}
