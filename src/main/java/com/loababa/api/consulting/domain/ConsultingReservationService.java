package com.loababa.api.consulting.domain;

import com.loababa.api.auth.domain.member.impl.repository.MemberReader;
import com.loababa.api.consulting.constant.ConsultingStatus;
import com.loababa.api.consulting.domain.impl.ReservationSlotAssembler;
import com.loababa.api.consulting.domain.impl.model.ReservationUpsertValidator;
import com.loababa.api.consulting.domain.impl.model.LossamSchedule;
import com.loababa.api.consulting.domain.impl.model.Reservation;
import com.loababa.api.consulting.domain.impl.model.ReservationAuthorizationValidator;
import com.loababa.api.consulting.domain.impl.model.ReservationListForms;
import com.loababa.api.consulting.domain.impl.model.ReservationSchedule;
import com.loababa.api.consulting.domain.impl.repository.ScheduleReader;
import com.loababa.api.consulting.domain.impl.repository.ReservationReader;
import com.loababa.api.consulting.domain.impl.repository.ReservationWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.loababa.api.consulting.constant.TimePolicy.RESERVATION_AVAILABLE_PERIOD;

@Service
@RequiredArgsConstructor
public class ConsultingReservationService {

    private final ReservationSlotAssembler reservationSlotAssembler;
    private final ReservationWriter reservationWriter;
    private final ReservationUpsertValidator reservationUpsertValidator;
    private final ReservationAuthorizationValidator reservationAuthorizationValidator;

    private final ReservationReader reservationReader;
    private final ScheduleReader scheduleReader;
    private final MemberReader memberReader;

    public ReservationSchedule getLossamSchedules(long lossamId) {
        LossamSchedule lossamSchedule = scheduleReader.readLossamSchedule(lossamId);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plus(RESERVATION_AVAILABLE_PERIOD);
        return reservationSlotAssembler.assembleReservationSchedule(lossamId, startDate, endDate, lossamSchedule);
    }

    public void upsertConsulting(Reservation reservation) {
        reservationUpsertValidator.validate(reservation);
        reservationWriter.upsert(reservation);
    }

    public ReservationListForms getMyConsulting(Long memberId, ConsultingStatus consultingStatus) {
        var memberType = memberReader.readMemberType(memberId);
        return switch (memberType) {
            case LOSSAM -> reservationReader.readLossamReservations(memberId, consultingStatus);
            case MOKOKO -> reservationReader.readMokokoReservations(memberId, consultingStatus);
        };
    }

    public Reservation getReservation(Long reservationId) {
        return reservationReader.read(reservationId);
    }

    public void approveReservation(Long reservationId, Long lossamId, Long dateTimeId) {
        reservationAuthorizationValidator.validateLossamReservation(reservationId, lossamId);
        reservationWriter.approve(reservationId, dateTimeId);
    }

    public void rejectReservation(Long reservationId, Long lossamId) {
        reservationAuthorizationValidator.validateLossamReservation(reservationId, lossamId);
        reservationWriter.reject(reservationId);
    }
}
