package com.loababa.api.consulting.persistence.adapter;

import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import com.loababa.api.consulting.domain.impl.model.LossamSchedule;
import com.loababa.api.consulting.domain.impl.model.TimeRange;
import com.loababa.api.consulting.domain.impl.repository.ConsultingScheduleReader;
import com.loababa.api.consulting.domain.impl.repository.ConsultingScheduleWriter;
import com.loababa.api.consulting.exception.ConsultingClientExceptionInfo;
import com.loababa.api.consulting.persistence.entity.ConsultingScheduleEntity;
import com.loababa.api.consulting.persistence.repository.ConsultingScheduleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConsultingScheduleJpaRepositoryAdapter implements ConsultingScheduleWriter, ConsultingScheduleReader {

    private final ConsultingScheduleJpaRepository consultingScheduleJpaRepository;

    @Override
    public void save(Long lossamId, LossamSchedule lossamSchedule) {
        var consultingScheduleEntities = new ArrayList<ConsultingScheduleEntity>();
        for (Map.Entry<DayOfWeek, TimeRange> entry : lossamSchedule.schedule().entrySet()) {
            var dayOfWeek = entry.getKey();
            var timeRange = entry.getValue();
            var consultingScheduleEntity = new ConsultingScheduleEntity(
                    dayOfWeek,
                    timeRange.start(), timeRange.end(),
                    lossamId
            );
            consultingScheduleEntities.add(consultingScheduleEntity);
        }
        consultingScheduleJpaRepository.saveAll(consultingScheduleEntities);
    }

    @Override
    public LossamSchedule readLossamSchedule(long lossamId) {
        var consultingScheduleEntities = consultingScheduleJpaRepository.findAllByMemberId(lossamId);
        if (consultingScheduleEntities.isEmpty()) {
            throw new LoababaBadRequestException(
                    ConsultingClientExceptionInfo.NOT_FOUND_SCHEDULE,
                    new ServerExceptionInfo("일정이 존재하지 않습니다.")
            );
        }
        Map<DayOfWeek, TimeRange> schedule = consultingScheduleEntities.stream()
                .collect(Collectors.toMap(
                        ConsultingScheduleEntity::getDayOfWeek,
                        consultingScheduleEntity -> new TimeRange(
                                consultingScheduleEntity.getStartTime(),
                                consultingScheduleEntity.getEndTime()
                        )
                ));
        return new LossamSchedule(schedule);
    }
}
