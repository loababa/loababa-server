package com.loababa.api.consulting.domain.impl.repository;

import com.loababa.api.consulting.domain.impl.model.LossamSchedule;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ConsultingScheduleReader {

    LossamSchedule readLossamSchedule(Long lossamId);

}
