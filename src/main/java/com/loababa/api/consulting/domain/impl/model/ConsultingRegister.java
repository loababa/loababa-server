package com.loababa.api.consulting.domain.impl.model;

import com.loababa.api.consulting.domain.impl.repository.ConsultingPostWriter;
import com.loababa.api.consulting.domain.impl.repository.ConsultingScheduleWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ConsultingRegister {

    private final ConsultingPostWriter consultingPostWriter;
    private final ConsultingScheduleWriter consultingScheduleWriter;

    @Transactional
    public void register(Long lossamId, ConsultingPost consultingPost, LossamSchedule lossamSchedule) {
        consultingPostWriter.save(lossamId, consultingPost);
        consultingScheduleWriter.save(lossamId, lossamSchedule);
    }
}
