package com.loababa.api.consulting.domain.impl;

import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.LoababaForbiddenException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import com.loababa.api.consulting.domain.impl.model.Reservation;
import com.loababa.api.consulting.domain.impl.model.ReservationDateTime;
import com.loababa.api.consulting.domain.impl.model.ReservationPreResponses;
import com.loababa.api.consulting.domain.impl.repository.ReservationReader;
import com.loababa.api.consulting.exception.ReservationClientExceptionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationUpsertValidator {

    private final ReservationReader reservationReader;

    public void validate(Reservation reservation) {
        if (isUpdateRequest(reservation.reservationId())) {
            validateUpdate(reservation);
        }
    }

    private boolean isUpdateRequest(Long reservationId) {
        return reservationId != null;
    }

    private void validateUpdate(Reservation reservationUpdateForm) {
        Reservation reservation = reservationReader.read(reservationUpdateForm.reservationId());
        if (!Objects.equals(reservation.mokokoId(), reservationUpdateForm.mokokoId())) {
            throw new LoababaForbiddenException(
                    ReservationClientExceptionInfo.FORBIDDEN,
                    new ServerExceptionInfo(
                            "수정 권한이 없는 사용자(mokokoId: " + reservationUpdateForm.mokokoId() + ")가 본인 소유가 아닌 예약을 수정하려고 시도했습니다."
                    )
            );
        }
        ReservationPreResponses reservationPreResponsesUpdateForm = reservationUpdateForm.reservationPreResponses();
        if (reservationPreResponsesUpdateForm.reservationPreResponsesId() == null) {
            throw new LoababaBadRequestException(
                    ReservationClientExceptionInfo.BAD_RESERVATION_UPDATE_REQUEST,
                    new ServerExceptionInfo("ReservationPreResponses id 값이 null입니다.")
            );
        }

        ReservationPreResponses reservationPreResponses = reservation.reservationPreResponses();
        if (!Objects.equals(
                reservationPreResponses.reservationPreResponsesId(),
                reservationPreResponsesUpdateForm.reservationPreResponsesId()
        )) {
            throw new LoababaBadRequestException(
                    ReservationClientExceptionInfo.BAD_RESERVATION_UPDATE_REQUEST,
                    new ServerExceptionInfo(
                            "PreResponsesUpdateForm id(" + reservationPreResponsesUpdateForm.reservationPreResponsesId() + ")값과" +
                                    "PreResponses id(" + reservationPreResponses.reservationPreResponsesId() + ") 값이 일치하지 않습니다.")
            );
        }

        Set<Long> updateFormDateTimeIds = reservationUpdateForm.reservationDateTimes().stream()
                .map(reservationDateTime -> {
                    if (reservationDateTime.reservationDateTimeId() == null) {
                        throw new LoababaBadRequestException(
                                ReservationClientExceptionInfo.BAD_RESERVATION_UPDATE_REQUEST,
                                new ServerExceptionInfo("ReservationDateTime id 값이 null입니다.")
                        );
                    }
                    return reservationDateTime.reservationDateTimeId();
                }).collect(Collectors.toUnmodifiableSet());

        Set<Long> existingDateTimeIds = reservation.reservationDateTimes()
                .stream()
                .map(ReservationDateTime::reservationDateTimeId)
                .collect(Collectors.toUnmodifiableSet());

        if (!updateFormDateTimeIds.equals(existingDateTimeIds)) {
            throw new LoababaBadRequestException(
                    ReservationClientExceptionInfo.BAD_RESERVATION_UPDATE_REQUEST,
                    new ServerExceptionInfo(
                            "ReservationDateTimes의 ID 값들이 일치하지 않습니다." +
                                    "업데이트 폼 ID 값들: " + updateFormDateTimeIds +
                                    " 기존 ID 값들: " + existingDateTimeIds
                    )
            );
        }
    }

}
