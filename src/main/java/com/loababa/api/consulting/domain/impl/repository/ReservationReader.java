package com.loababa.api.consulting.domain.impl.repository;

import com.loababa.api.consulting.constant.ConsultingStatus;
import com.loababa.api.consulting.domain.impl.model.ConsultingReservations;
import com.loababa.api.consulting.domain.impl.model.Reservation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
public interface ReservationReader {

    boolean existsReservation(long lossamId, LocalDateTime slotStartTime, LocalDateTime slotEndTime);

    Reservation read(Long reservationId);

    ConsultingReservations readLossamConsultingReservations(Long memberId, ConsultingStatus consultingStatus);

    ConsultingReservations readMokokoConsultingReservations(Long memberId, ConsultingStatus consultingStatus);

}
