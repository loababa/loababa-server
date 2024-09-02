package com.loababa.api.consulting.domain;

import com.loababa.api.auth.domain.member.impl.repository.MemberReader;
import com.loababa.api.consulting.constant.ConsultingStatus;
import com.loababa.api.consulting.domain.impl.ReservationSlotAssembler;
import com.loababa.api.consulting.domain.impl.model.ConsultingReservationForm;
import com.loababa.api.consulting.domain.impl.model.ConsultingReservations;
import com.loababa.api.consulting.domain.impl.model.LossamSchedule;
import com.loababa.api.consulting.domain.impl.model.ReservationSchedule;
import com.loababa.api.consulting.domain.impl.repository.ConsultingReservationReader;
import com.loababa.api.consulting.domain.impl.repository.ConsultingScheduleReader;
import com.loababa.api.consulting.domain.impl.repository.ReservationWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.loababa.api.consulting.constant.TimePolicy.RESERVATION_AVAILABLE_PERIOD;

@Service
@RequiredArgsConstructor
public class ConsultingReservationService {

    private final ReservationSlotAssembler reservationSlotAssembler;

    private final MemberReader memberReader;
    private final ConsultingScheduleReader consultingScheduleReader;
    private final ReservationWriter consultingReservationWriter;
    private final ConsultingReservationReader consultingReservationReader;

    public ReservationSchedule getLossamSchedules(long lossamId) {
        LossamSchedule lossamSchedule = consultingScheduleReader.readLossamSchedule(lossamId);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plus(RESERVATION_AVAILABLE_PERIOD);
        return reservationSlotAssembler.assembleReservationSchedule(lossamId, startDate, endDate, lossamSchedule);
    }

    public void reserveConsulting(ConsultingReservationForm consultingReservationForm) {
        consultingReservationWriter.save(consultingReservationForm);
    }

    public ConsultingReservations getMyConsulting(Long memberId, ConsultingStatus consultingStatus) {
        var memberType = memberReader.readMemberType(memberId);
        return switch (memberType) {
            case LOSSAM -> consultingReservationReader.readLossamConsultingReservations(memberId, consultingStatus);
            case MOKOKO -> consultingReservationReader.readMokokoConsultingReservations(memberId, consultingStatus);
        };
    }

}
