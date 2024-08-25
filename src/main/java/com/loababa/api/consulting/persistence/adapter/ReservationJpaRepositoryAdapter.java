package com.loababa.api.consulting.persistence.adapter;

import com.loababa.api.consulting.domain.impl.model.ConsultingReservationForm;
import com.loababa.api.consulting.domain.impl.repository.ReservationReader;
import com.loababa.api.consulting.domain.impl.repository.ReservationWriter;
import com.loababa.api.consulting.persistence.entity.ReservationDateTimeEntity;
import com.loababa.api.consulting.persistence.entity.ReservationEntity;
import com.loababa.api.consulting.persistence.entity.ReservationPreResponsesEntity;
import com.loababa.api.consulting.persistence.repository.ReservationDateTimeJpaRepository;
import com.loababa.api.consulting.persistence.repository.ReservationJpaRepository;
import com.loababa.api.consulting.persistence.repository.ReservationPreResponsesJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReservationJpaRepositoryAdapter implements ReservationReader, ReservationWriter {

    private final ReservationJpaRepository reservationJpaRepository;
    private final ReservationPreResponsesJpaRepository reservationPreResponsesJpaRepository;
    private final ReservationDateTimeJpaRepository reservationDateTimeJpaRepository;

    @Override
    public void save(ConsultingReservationForm consultingReservationForm) {
        ReservationEntity reservation = reservationJpaRepository.save(
                ReservationEntity.builder()
                        .lossamId(consultingReservationForm.lossamId())
                        .mokokoId(consultingReservationForm.memberId())
                        .build()
        );

        reservationDateTimeJpaRepository.saveAll(
                consultingReservationForm.reservationDateTimes().stream()
                        .map(dateTimeRange -> ReservationDateTimeEntity.builder()
                                .startDateTime(dateTimeRange.start())
                                .endDateTime(dateTimeRange.end())
                                .reservationId(reservation.getId())
                                .build()
                        ).toList()
        );

        reservationPreResponsesJpaRepository.save(
                ReservationPreResponsesEntity.builder()
                        .characterDetails(consultingReservationForm.characterDetails())
                        .inquiryDetails(consultingReservationForm.inquiryDetails())
                        .experience(consultingReservationForm.experience())
                        .contactNumber(consultingReservationForm.contactNumber())
                        .memberId(consultingReservationForm.memberId())
                        .reservationId(reservation.getId())
                        .build()
        );
    }

    @Override
    public boolean existsReservation(long lossamId, LocalDateTime slotStartTime, LocalDateTime slotEndTime) {
        return reservationJpaRepository.existsReservation(lossamId, slotStartTime, slotEndTime);
    }
}
