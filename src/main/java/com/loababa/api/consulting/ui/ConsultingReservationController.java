package com.loababa.api.consulting.ui;

import com.loababa.api.common.model.ApiResponse;
import com.loababa.api.common.model.LossamCredential;
import com.loababa.api.common.model.MemberCredential;
import com.loababa.api.consulting.constant.ConsultingStatus;
import com.loababa.api.consulting.domain.ConsultingReservationService;
import com.loababa.api.consulting.domain.impl.model.DaySchedule;
import com.loababa.api.consulting.domain.impl.model.Reservation;
import com.loababa.api.consulting.domain.impl.model.ReservationListForms;
import com.loababa.api.consulting.domain.impl.model.ReservationSchedule;
import com.loababa.api.consulting.ui.dto.ConsultingReservationReq;
import com.loababa.api.consulting.ui.dto.ReservationApproveReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Consulting", description = "상담 예약 관련 API")
@RestController
@RequiredArgsConstructor
public class ConsultingReservationController {

    private final ConsultingReservationService consultingReservationService;

    @Operation(description = "로쌤 상담 시간 가져오기", security = @SecurityRequirement(name = "Authorization"))
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DaySchedule.class),
                            examples = @ExampleObject(
                                    value = "{\"code\":\"OK\",\"message\":\"성공\",\"data\":[{\"isBusinessDay\":true,\"date\":\"2024-08-27\",\"slots\":[{\"timeRange\":{\"start\":\"09:00:00\",\"end\":\"10:00:00\"},\"isAvailable\":true}]}]}"
                            )
                    )
            )
    })
    @GetMapping("/api/v1/consulting/schedules")
    public ApiResponse<List<DaySchedule>> requestConsultingSchedule(
            MemberCredential memberCredential,
            @RequestParam long lossamId
    ) {
        ReservationSchedule lossamSchedules = consultingReservationService.getLossamSchedules(lossamId);
        return ApiResponse.success(lossamSchedules.value());
    }

    @Operation(description = "상담 요청(생성) 및 수정", security = @SecurityRequirement(name = "Authorization"))
    @PutMapping("/api/v1/consulting/reservations")
    public ApiResponse<Void> requestConsultingReservation(
            MemberCredential memberCredential,
            @Valid @RequestBody ConsultingReservationReq request
    ) {
        consultingReservationService.upsertConsulting(
                request.toReservation(memberCredential.memberId())
        );
        return ApiResponse.success();
    }

    @Operation(description = "나의 상담 예약 불러오기", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/api/v1/consulting/reservations/my")
    public ApiResponse<ReservationListForms> requestMyConsulting(
            MemberCredential memberCredential,
            @Schema(allowableValues = {"pending", "confirmed", "past"},
                    description = "불러올 상담 상태")
            @RequestParam String status
    ) {
        ReservationListForms myReservationListForms = consultingReservationService.getMyConsulting(
                memberCredential.memberId(),
                ConsultingStatus.from(status)
        );
        return ApiResponse.success(myReservationListForms);
    }

    @Operation(description = "상담 예약 상세 보기", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/api/v1/consulting/reservations/{reservationId}")
    public ApiResponse<Reservation> approveReservation(
            MemberCredential memberCredential,
            @PathVariable @NotNull Long reservationId
    ) {
        Reservation reservation = consultingReservationService.getReservation(reservationId);
        return ApiResponse.success(reservation);
    }

    @Operation(description = "상담 확정 하기", security = @SecurityRequirement(name = "Authorization"))
    @PatchMapping("/api/v1/consulting/reservations/{reservationId}/approve")
    public ApiResponse<Void> approveReservation(
            LossamCredential lossamCredential,
            @PathVariable @NotNull Long reservationId,
            @RequestBody ReservationApproveReq reservationApproveReq
    ) {
        consultingReservationService.approveReservation(
                reservationId,
                lossamCredential.lossamId(),
                reservationApproveReq.dateTimeId()
        );
        return ApiResponse.success();
    }

    @Operation(description = "상담 거절 하기", security = @SecurityRequirement(name = "Authorization"))
    @PatchMapping("/api/v1/consulting/reservations/{reservationId}/reject")
    public ApiResponse<Void> rejectReservation(
            LossamCredential lossamCredential,
            @PathVariable @NotNull Long reservationId
    ) {
        consultingReservationService.rejectReservation(reservationId, lossamCredential.lossamId());
        return ApiResponse.success();
    }

}
