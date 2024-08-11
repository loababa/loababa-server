package com.loababa.api.consulting.domain;

import com.loababa.api.consulting.domain.impl.model.LossamSchedule;
import com.loababa.api.consulting.domain.impl.repository.ConsultingScheduleReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultingRequestService {

    private final ConsultingScheduleReader consultingScheduleReader;

    public LossamSchedule getLossamSchedules(Long lossamId) {
        return consultingScheduleReader.readLossamSchedule(lossamId);
    }

}
