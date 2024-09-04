package com.loababa.api.consulting.persistence.adapter;

import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import com.loababa.api.consulting.domain.impl.model.Reservation;
import com.loababa.api.consulting.domain.impl.model.ReservationDateTime;
import com.loababa.api.consulting.domain.impl.model.ReservationPreResponses;
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
import java.util.List;

import static com.loababa.api.consulting.exception.ReservationClientExceptionInfo.NOT_FOUND_RESERVATION;

@Component
@RequiredArgsConstructor
public class ReservationJpaRepositoryAdapter implements ReservationReader, ReservationWriter {

    private final ReservationJpaRepository reservationJpaRepository;
    private final ReservationPreResponsesJpaRepository reservationPreResponsesJpaRepository;
    private final ReservationDateTimeJpaRepository reservationDateTimeJpaRepository;

    @Override
    public void upsert(Reservation reservation) {
        ReservationEntity reservationEntity = reservationJpaRepository.save(
                ReservationEntity.builder()
                        .id(reservation.reservationId())
                        .lossamId(reservation.lossamId())
                        .mokokoId(reservation.mokokoId())
                        .build()
        );

        List<ReservationDateTime> reservationDateTimes = reservation.reservationDateTimes();
        reservationDateTimeJpaRepository.saveAll(
                reservationDateTimes.stream()
                        .map(reservationDateTime -> ReservationDateTimeEntity.builder()
                                .id(reservationDateTime.reservationDateTimeId())
                                .startDateTime(reservationDateTime.getStartTime())
                                .endDateTime(reservationDateTime.getEndTime())
                                .reservationId(reservationEntity.getId())
                                .build()
                        ).toList()
        );

        ReservationPreResponses reservationPreResponses = reservation.reservationPreResponses();
        reservationPreResponsesJpaRepository.save(
                ReservationPreResponsesEntity.builder()
                        .characterDetails(reservationPreResponses.characterDetails())
                        .inquiryDetails(reservationPreResponses.inquiryDetails())
                        .experience(reservationPreResponses.experience())
                        .contactNumber(reservationPreResponses.contactNumber())
                        .memberId(reservation.mokokoId())
                        .reservationId(reservationEntity.getId())
                        .build()
        );
    }

    @Override
    public boolean existsReservation(long lossamId, LocalDateTime slotStartTime, LocalDateTime slotEndTime) {
        return reservationJpaRepository.existsReservation(lossamId, slotStartTime, slotEndTime);
    }

    @Override
    public Reservation read(Long reservationId) {
        ReservationEntity reservationEntity = reservationJpaRepository.findById(reservationId)
                .orElseThrow(() -> new LoababaBadRequestException(
                        NOT_FOUND_RESERVATION,
                        new ServerExceptionInfo("존재 하지 않는 Reservation id: " + reservationId)
                ));

        var reservationDateTimeEntities
                = reservationDateTimeJpaRepository.findByReservationId(reservationId);

        var reservationPreResponses
                = reservationPreResponsesJpaRepository.findByReservationId(reservationId);

        return new Reservation(
                reservationId,
                reservationPreResponses.toReservationPreResponses(),
                reservationDateTimeEntities.stream()
                        .map(ReservationDateTimeEntity::toReservationDateTime)
                        .toList(),
                reservationEntity.getLossamId(),
                reservationEntity.getMokokoId()
        );
    }

}
