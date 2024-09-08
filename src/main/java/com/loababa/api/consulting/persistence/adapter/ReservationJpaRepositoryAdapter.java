package com.loababa.api.consulting.persistence.adapter;

import com.loababa.api.auth.domain.member.impl.model.MemberType;
import com.loababa.api.consulting.constant.ConsultingStatus;
import com.loababa.api.consulting.domain.impl.model.DateTimeRange;
import com.loababa.api.consulting.domain.impl.model.Reservation;
import com.loababa.api.consulting.domain.impl.model.ReservationDateTime;
import com.loababa.api.consulting.domain.impl.model.ReservationListForms;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.loababa.api.auth.domain.member.impl.model.MemberType.LOSSAM;
import static com.loababa.api.auth.domain.member.impl.model.MemberType.MOKOKO;
import static com.loababa.api.consulting.constant.ConsultingStatus.PENDING;

@Component
@RequiredArgsConstructor
public class ReservationJpaRepositoryAdapter implements ReservationReader, ReservationWriter {

    private final ReservationJpaRepository reservationJpaRepository;
    private final ReservationPreResponsesJpaRepository reservationPreResponsesJpaRepository;
    private final ReservationDateTimeJpaRepository reservationDateTimeJpaRepository;

    @Transactional
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
                                .startDateTime(reservationDateTime.startTime())
                                .endDateTime(reservationDateTime.endTime())
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

    @Transactional
    @Override
    public void approve(Long reservationId, Long dateTimeId) {
        ReservationEntity reservationEntity = reservationJpaRepository.getReservationEntityById(reservationId);
        reservationEntity.approved();

        ReservationDateTimeEntity dateTimeEntity =
                reservationDateTimeJpaRepository.getReservationDateTimeEntityById(dateTimeId);
        dateTimeEntity.approved();
    }

    @Transactional
    @Override
    public void reject(Long reservationId) {
        ReservationEntity reservationEntity = reservationJpaRepository.getReservationEntityById(reservationId);
        reservationEntity.rejected();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsReservation(long lossamId, LocalDateTime slotStartTime, LocalDateTime slotEndTime) {
        return reservationJpaRepository.existsReservation(lossamId, slotStartTime, slotEndTime);
    }

    @Transactional(readOnly = true)
    @Override
    public Reservation read(Long reservationId) {
        ReservationEntity reservationEntity = reservationJpaRepository.getReservationEntityById(reservationId);

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

    @Transactional(readOnly = true)
    @Override
    public ReservationListForms readLossamReservations(
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
        return buildReservations(consultingStatus, lossamReservations, reservationPreResponses, reservationDateTime, LOSSAM);
    }

    @Transactional(readOnly = true)
    @Override
    public ReservationListForms readMokokoReservations(Long mokokoId, ConsultingStatus consultingStatus) {
        var mokokoReservations =
                reservationJpaRepository.findAllByMokokoIdAndConsultingStatus(mokokoId, consultingStatus);
        var mokokoReservationIds = mokokoReservations.stream()
                .map(ReservationEntity::getId)
                .collect(Collectors.toSet());
        var reservationDateTime = mapReservationIdsToDateTimes(mokokoReservationIds);

        return buildReservations(consultingStatus, mokokoReservations, new HashMap<>(), reservationDateTime, MOKOKO);
    }

    private Map<Long, List<ReservationDateTimeEntity>> mapReservationIdsToDateTimes(Set<Long> lossamReservationIds) {
        return reservationDateTimeJpaRepository.findByReservationIdIn(lossamReservationIds).stream()
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

    private ReservationListForms buildReservations(
            ConsultingStatus consultingStatus,
            List<ReservationEntity> reservations,
            Map<Long, String> reservationPreResponses,
            Map<Long, List<ReservationDateTimeEntity>> reservationDateTime,
            MemberType memberType
    ) {
        List<ReservationListForms.ReservationListForm> reservationListForms = reservations.stream()
                .map(reservation -> {
                    String inquiryDetails = reservationPreResponses.getOrDefault(reservation.getId(), null);
                    var dateTimeRanges = reservationDateTime.get(reservation.getId()).stream()
                            // PENDING 일 경우 모든 예약 시간을 보여주고, CONFIRMED, PAST의 경우에는 확정된 예약만 보여준다.
                            .filter(dateTime -> consultingStatus == PENDING || dateTime.isConfirmed())
                            .map(dateTime -> new DateTimeRange(dateTime.getStartDateTime(), dateTime.getEndDateTime()))
                            .toList();
                    return new ReservationListForms.ReservationListForm(
                            reservation.getId(),
                            reservation.getLossamId(),
                            reservation.getMokokoId(),
                            inquiryDetails,
                            dateTimeRanges
                    );
                }).toList();

        return ReservationListForms.from(
                reservationListForms,
                memberType
        );
    }
}
