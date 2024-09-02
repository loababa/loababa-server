package com.loababa.api.consulting.domain.impl.repository;

import com.loababa.api.consulting.constant.ConsultingStatus;
import com.loababa.api.consulting.domain.impl.model.ConsultingReservations;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ConsultingReservationReader {

    ConsultingReservations readLossamConsultingReservations(Long memberId, ConsultingStatus consultingStatus);

    ConsultingReservations readMokokoConsultingReservations(Long memberId, ConsultingStatus consultingStatus);

}
