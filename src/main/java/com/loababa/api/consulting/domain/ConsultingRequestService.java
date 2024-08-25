package com.loababa.api.consulting.domain;

import com.loababa.api.consulting.domain.impl.ReservationSlotAssembler;
import com.loababa.api.consulting.domain.impl.model.ConsultingReservationForm;
import com.loababa.api.consulting.domain.impl.model.LossamSchedule;
import com.loababa.api.consulting.domain.impl.model.ReservationSchedule;
import com.loababa.api.consulting.domain.impl.repository.ConsultingScheduleReader;
import com.loababa.api.consulting.domain.impl.repository.ReservationWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.loababa.api.consulting.constant.TimePolicy.RESERVATION_AVAILABLE_PERIOD;

@Service
@RequiredArgsConstructor
public class ConsultingRequestService {

    private final ConsultingScheduleReader consultingScheduleReader;
    private final ReservationWriter consultingReservationWriter;
    private final ReservationSlotAssembler reservationSlotAssembler;

    public ReservationSchedule getLossamSchedules(long lossamId) {
        LossamSchedule lossamSchedule = consultingScheduleReader.readLossamSchedule(lossamId);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plus(RESERVATION_AVAILABLE_PERIOD);
        return reservationSlotAssembler.assembleReservationSchedule(lossamId, startDate, endDate, lossamSchedule);
    }

    public void reserveConsulting(ConsultingReservationForm consultingReservationForm) {
        consultingReservationWriter.save(consultingReservationForm);
    }

}
