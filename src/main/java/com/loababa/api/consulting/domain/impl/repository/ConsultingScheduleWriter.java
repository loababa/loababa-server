package com.loababa.api.consulting.domain.impl.repository;

import com.loababa.api.consulting.domain.impl.model.LossamSchedule;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ConsultingScheduleWriter {

    void save(Long lossamId, LossamSchedule lossamSchedule);

}
