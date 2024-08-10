package com.loababa.api.consulting.ui;

import com.loababa.api.common.model.AuthCredential;
import com.loababa.api.common.model.ApiResponse;
import com.loababa.api.consulting.domain.ConsultingService;
import com.loababa.api.consulting.domain.impl.model.ConsultingDetailForm;
import com.loababa.api.consulting.domain.impl.model.ConsultingListForms;
import com.loababa.api.consulting.ui.dto.ConsultingRegistrationReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Consulting", description = "상담 관련 API")
@RestController
@RequiredArgsConstructor
public class ConsultingController {

    private final ConsultingService consultingService;

    @Operation(description = "멘토링 포스트 등록")
    @PostMapping("/api/consulting")
    public ApiResponse<Void> requestConsultingRegistration(
            AuthCredential credential,
            @Schema(description = "로쌤 멘토링 포스트")
            @RequestBody @Valid ConsultingRegistrationReq consultingRegistrationReq
    ) {
        consultingService.registerConsulting(
                credential.memberId(),
                consultingRegistrationReq.toLossamConsultingPost(),
                consultingRegistrationReq.toLossamSchedule()
        );
        return ApiResponse.success();
    }

    @Operation(description = "멘토링 리스트 불러오기")
    @GetMapping("/api/consulting")
    public ApiResponse<ConsultingListForms> requestConsultingList(
            AuthCredential authCredential
    ) {
        ConsultingListForms allConsultingListForms = consultingService.getAllConsultingListForms();
        return ApiResponse.success(allConsultingListForms);
    }

    @Operation(description = "멘토링 상세 불러오기")
    @GetMapping("/api/consulting/{consultingPostId}")
    public ApiResponse<ConsultingDetailForm> requestConsultingDetail(
            AuthCredential authCredential,
            @PathVariable @NotNull Long consultingPostId
    ) {
        var consultingDetailForm = consultingService.getConsultingDetailForm(consultingPostId);
        return ApiResponse.success(consultingDetailForm);
    }

}
