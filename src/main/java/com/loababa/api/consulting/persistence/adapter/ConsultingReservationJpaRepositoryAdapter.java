package com.loababa.api.consulting.persistence.adapter;

import com.loababa.api.auth.domain.member.impl.model.MemberType;
import com.loababa.api.consulting.constant.ConsultingStatus;
import com.loababa.api.consulting.domain.impl.model.ConsultingReservations;
import com.loababa.api.consulting.domain.impl.model.ConsultingReservations.ConsultingReservation;
import com.loababa.api.consulting.domain.impl.model.DateTimeRange;
import com.loababa.api.consulting.domain.impl.repository.ConsultingReservationReader;
import com.loababa.api.consulting.persistence.entity.ReservationDateTimeEntity;
import com.loababa.api.consulting.persistence.entity.ReservationEntity;
import com.loababa.api.consulting.persistence.entity.ReservationPreResponsesEntity;
import com.loababa.api.consulting.persistence.repository.ReservationDateTimeJpaRepository;
import com.loababa.api.consulting.persistence.repository.ReservationJpaRepository;
import com.loababa.api.consulting.persistence.repository.ReservationPreResponsesJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.loababa.api.auth.domain.member.impl.model.MemberType.LOSSAM;

@Component
@RequiredArgsConstructor
public class ConsultingReservationJpaRepositoryAdapter implements ConsultingReservationReader {

    private final ReservationJpaRepository reservationJpaRepository;
    private final ReservationDateTimeJpaRepository dateTimeJpaRepository;
    private final ReservationPreResponsesJpaRepository reservationPreResponsesJpaRepository;

    @Override
    public ConsultingReservations readLossamConsultingReservations(
            Long lossamId,
            ConsultingStatus consultingStatus
    ) {
        var lossamReservations =
                reservationJpaRepository.findAllByLossamIdAndConsultingStatus(lossamId, consultingStatus);

        var lossamReservationIds = lossamReservations.stream()
                .map(ReservationEntity::getId)
                .collect(Collectors.toSet());
        var reservationDateTime = mapReservationIdsToDateTimes(lossamReservationIds);
        var reservationPreResponses = mapReservationIdsToPreResponses(lossamReservationIds);
        return buildLossamConsultingReservations(consultingStatus, lossamReservations, reservationPreResponses, reservationDateTime, LOSSAM);
    }

    @Override
    public ConsultingReservations readMokokoConsultingReservations(Long mokokoId, ConsultingStatus consultingStatus) {
        var mokokoReservations =
                reservationJpaRepository.findAllByMokokoIdAndConsultingStatus(mokokoId, consultingStatus);
        var mokokoReservationIds = mokokoReservations.stream()
                .map(ReservationEntity::getId)
                .collect(Collectors.toSet());
        var reservationDateTime = mapReservationIdsToDateTimes(mokokoReservationIds);

        return buildLossamConsultingReservations(consultingStatus, mokokoReservations, new HashMap<>(), reservationDateTime, LOSSAM);
    }

    private Map<Long, List<ReservationDateTimeEntity>> mapReservationIdsToDateTimes(Set<Long> lossamReservationIds) {
        return dateTimeJpaRepository.findByReservationIdIn(lossamReservationIds).stream()
                .collect(Collectors.groupingBy(
                        ReservationDateTimeEntity::getReservationId,
                        Collectors.mapping(
                                dateTime -> dateTime,
                                Collectors.toList()
                        )
                ));
    }

    private Map<Long, String> mapReservationIdsToPreResponses(Set<Long> lossamReservationIds) {
        return reservationPreResponsesJpaRepository.findByReservationIdIn(lossamReservationIds).stream()
                .collect(Collectors.toMap(
                        ReservationPreResponsesEntity::getReservationId,
                        ReservationPreResponsesEntity::getInquiryDetails
                ));
    }

    private ConsultingReservations buildLossamConsultingReservations(
            ConsultingStatus consultingStatus,
            List<ReservationEntity> reservations,
            Map<Long, String> reservationPreResponses,
            Map<Long, List<ReservationDateTimeEntity>> reservationDateTime,
            MemberType memberType
    ) {
        return switch (consultingStatus) {
            case PENDING -> ConsultingReservations.from(
                    reservations.stream()
                            .map(reservation -> {
                                String inquiryDetails = reservationPreResponses.getOrDefault(reservation.getId(), null);
                                var dateTimeRanges = reservationDateTime.get(reservation.getId()).stream()
                                        .map(dateTime -> new DateTimeRange(
                                                dateTime.getStartDateTime(),
                                                dateTime.getEndDateTime()
                                        ))
                                        .toList();
                                return new ConsultingReservation(
                                        reservation.getId(),
                                        reservation.getLossamId(),
                                        reservation.getMokokoId(),
                                        inquiryDetails,
                                        dateTimeRanges
                                );
                            }).toList(),
                    memberType
            );
            case CONFIRMED, PAST -> ConsultingReservations.from(
                    reservations.stream()
                            .map(reservation -> {
                                String inquiryDetails = reservationPreResponses.getOrDefault(reservation.getId(), null);
                                var dateTimeRanges = reservationDateTime.get(reservation.getId()).stream()
                                        .filter(ReservationDateTimeEntity::isConfirmed)
                                        .map(dateTime -> new DateTimeRange(dateTime.getStartDateTime(), dateTime.getEndDateTime()))
                                        .toList();
                                return new ConsultingReservation(
                                        reservation.getId(),
                                        reservation.getLossamId(),
                                        reservation.getMokokoId(),
                                        inquiryDetails,
                                        dateTimeRanges
                                );
                            }).toList(),
                    memberType
            );
        };
    }

}
