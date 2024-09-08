package com.loababa.api.consulting.domain.impl.repository;

import com.loababa.api.consulting.constant.ConsultingStatus;
import com.loababa.api.consulting.domain.impl.model.ReservationListForms;
import com.loababa.api.consulting.domain.impl.model.Reservation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
public interface ReservationReader {

    boolean existsReservation(long lossamId, LocalDateTime slotStartTime, LocalDateTime slotEndTime);

    Reservation read(Long reservationId);

    ReservationListForms readLossamReservations(Long memberId, ConsultingStatus consultingStatus);

    ReservationListForms readMokokoReservations(Long memberId, ConsultingStatus consultingStatus);

}
