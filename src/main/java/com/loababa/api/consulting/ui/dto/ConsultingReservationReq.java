package com.loababa.api.consulting.ui.dto;

import com.loababa.api.consulting.domain.impl.model.DateTimeRange;
import com.loababa.api.consulting.domain.impl.model.Reservation;
import com.loababa.api.consulting.domain.impl.model.ReservationDateTime;
import com.loababa.api.consulting.domain.impl.model.ReservationPreResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

import java.util.List;

public record ConsultingReservationReq(

        @Positive
        @Nullable
        @Schema(nullable = true)
        Long reservationId,

        @NotNull
        ReservationPreResponsesDto reservationPreResponses,

        @NotNull(message = "일정을 선택해주세요.")
        @Size(min = 1, max = 3, message = "최소 1개, 최대 3개의 일정을 선택해야 합니다.")
        List<ReservationDateTimeDto> reservationDateTimes,

        @NotNull
        @Positive
        Long lossamId
) {

    public Reservation toReservation(Long mokokoId) {
        // TODO: 이 Reservation 을 만들때부터 UpdateForm 인지 SaveForm인지 정하는 게 좋을 듯
        return new Reservation(
                reservationId,
                new ReservationPreResponses(
                        reservationPreResponses.reservationPreResponsesId(),
                        reservationPreResponses.characterDetails(),
                        reservationPreResponses.inquiryDetails(),
                        reservationPreResponses.experience(),
                        reservationPreResponses.contactNumber()
                ),
                reservationDateTimes().stream()
                        .map(reservationDateTimeDto ->
                                new ReservationDateTime(
                                        reservationDateTimeDto.reservationDateTimeId,
                                        reservationDateTimeDto.dateTimeRange
                                )
                        ).toList(),
                lossamId,
                mokokoId
        );
    }


    public record ReservationDateTimeDto(
            @Positive
            @Nullable
            @Schema(nullable = true)
            Long reservationDateTimeId,

            DateTimeRange dateTimeRange
    ) {

    }

    public record ReservationPreResponsesDto(
            @Positive
            @Nullable
            @Schema(nullable = true)
            Long reservationPreResponsesId,

            @NotBlank(message = "상담 캐릭터명, 직업 각인, 아이템 레벨을 입력해주세요.")
            String characterDetails,

            @NotBlank(message = "상세 사항을 입력해주세요.")
            String inquiryDetails,

            @NotBlank(message = "로아 경력을 입력해주세요.")
            String experience,

            @NotBlank(message = "알림 받을 연락처를 입력해주세요.")
            @Pattern(regexp = "^010\\d{8}$", message = "알림 받을 연락처는 010으로 시작하고, 총 11자리의 숫자여야 합니다.")
            String contactNumber
    ) {

    }
}
