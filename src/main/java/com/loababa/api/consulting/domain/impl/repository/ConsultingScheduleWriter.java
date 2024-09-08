package com.loababa.api.consulting.domain.impl.repository;

import com.loababa.api.consulting.domain.impl.model.LossamSchedule;

public interface ConsultingScheduleWriter {

    void save(Long lossamId, LossamSchedule lossamSchedule);

}
