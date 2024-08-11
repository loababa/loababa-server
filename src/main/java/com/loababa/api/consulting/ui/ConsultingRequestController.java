package com.loababa.api.consulting.ui;

import com.loababa.api.common.model.ApiResponse;
import com.loababa.api.common.model.MemberCredential;
import com.loababa.api.consulting.domain.ConsultingRequestService;
import com.loababa.api.consulting.domain.impl.model.LossamSchedule;
import com.loababa.api.consulting.ui.dto.ConsultingScheduleRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConsultingRequestController {

    private final ConsultingRequestService consultingRequestService;

    @Operation(description = "로쌤 상담 시간 가져오기", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/api/v1/consulting/schedules")
    public ApiResponse<ConsultingScheduleRes> requestConsultingSchedule(
            MemberCredential memberCredential,
            @NotBlank
            @RequestParam Long lossamId
    ) {
        LossamSchedule lossamSchedules = consultingRequestService.getLossamSchedules(lossamId);
        return ApiResponse.success(
                new ConsultingScheduleRes(lossamSchedules.schedule())
        );
    }

}
