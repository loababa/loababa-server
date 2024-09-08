package com.loababa.api.consulting.domain.impl.model;

import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;
import com.loababa.api.consulting.domain.impl.repository.ReservationReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.loababa.api.consulting.exception.ReservationClientExceptionInfo.FORBIDDEN;

@Component
@RequiredArgsConstructor
public class ReservationAuthorizationValidator {

    private final ReservationReader reservationReader;

    @Transactional(readOnly = true)
    public void validateLossamReservation(Long reservationId, Long lossamId) {
        Reservation reservation = this.reservationReader.read(reservationId);
        if (!Objects.equals(reservation.lossamId(), lossamId)) {
            throw new LoababaBadRequestException(
                    FORBIDDEN,
                    new ServerExceptionInfo(
                            "예약 정보(" + reservationId + ")와 Lossam 정보(" + lossamId + ")가 일치하지 않습니다."
                    )
            );
        }
    }
}
