package com.loababa.api.consulting.domain.impl.repository;

import com.loababa.api.consulting.domain.impl.model.ConsultingReservationForm;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReservationWriter {

    void save(ConsultingReservationForm consultingReservationForm);

}
