package com.loababa.api.consulting.domain.impl.repository;

import com.loababa.api.consulting.domain.impl.model.ConsultingDetailForm;
import com.loababa.api.consulting.domain.impl.model.ConsultingPostListForms;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public interface ConsultingPostReader {

    ConsultingPostListForms getAllConsultingPostListForm(List<Long> allLossamId);

    ConsultingDetailForm readConsultingDetailForm(Long consultingPostId);

}
