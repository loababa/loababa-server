package com.loababa.api.consulting.ui;

import com.loababa.api.common.model.ApiResponse;
import com.loababa.api.common.model.MemberCredential;
import com.loababa.api.consulting.domain.ConsultingRequestService;
import com.loababa.api.consulting.domain.impl.model.ConsultingReservationForm;
import com.loababa.api.consulting.domain.impl.model.DaySchedule;
import com.loababa.api.consulting.domain.impl.model.ReservationSchedule;
import com.loababa.api.consulting.ui.dto.ConsultingReservationReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Consulting", description = "상담 관련 API")
@RestController
@RequiredArgsConstructor
public class ConsultingRequestController {

    private final ConsultingRequestService consultingRequestService;

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
        ReservationSchedule lossamSchedules = consultingRequestService.getLossamSchedules(lossamId);
        return ApiResponse.success(lossamSchedules.value());
    }

    @Operation(description = "상담 요청하기", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/api/v1/consulting/reservation")
    public ApiResponse<Void> requestConsulting(
            MemberCredential memberCredential,
            @Valid @RequestBody ConsultingReservationReq request
    ) {
        consultingRequestService.reserveConsulting(
                new ConsultingReservationForm(
                        request.characterDetails(),
                        request.inquiryDetails(),
                        request.experience(),
                        request.contactNumber(),
                        request.reservationDateTimes(),
                        request.lossamId(),
                        memberCredential.memberId()
                )
        );
        return ApiResponse.success();
    }

}
