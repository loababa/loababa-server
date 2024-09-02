package com.loababa.api.consulting.ui;

import com.loababa.api.common.model.ApiResponse;
import com.loababa.api.common.model.LossamCredential;
import com.loababa.api.common.model.MemberCredential;
import com.loababa.api.consulting.domain.ConsultingService;
import com.loababa.api.consulting.domain.impl.model.ConsultingPostDetailForm;
import com.loababa.api.consulting.domain.impl.model.ConsultingListForms;
import com.loababa.api.consulting.ui.dto.ConsultingRegistrationReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(description = "상담 포스트 등록하기", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/api/v1/consulting")
    public ApiResponse<Void> requestConsultingRegistration(
            LossamCredential credential,
            @RequestBody @Valid ConsultingRegistrationReq consultingRegistrationReq
    ) {
        consultingService.registerConsulting(
                credential.memberId(),
                consultingRegistrationReq.toLossamConsultingPost(),
                consultingRegistrationReq.toLossamSchedule()
        );
        return ApiResponse.success();
    }

    @Operation(description = "전체 상담 리스트 불러오기")
    @GetMapping("/api/v1/consulting/posts")
    public ApiResponse<ConsultingListForms> requestConsultingList() {
        ConsultingListForms allConsultingListForms = consultingService.getAllConsultingListForms();
        return ApiResponse.success(allConsultingListForms);
    }

    @Operation(description = "특정 상담 포스팅 상세 불러오기", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/api/v1/consulting/posts/{consultingPostId}")
    public ApiResponse<ConsultingPostDetailForm> requestConsultingDetail(
            MemberCredential memberCredential,
            @PathVariable @NotNull Long consultingPostId
    ) {
        var consultingDetailForm = consultingService.getConsultingDetailForm(consultingPostId);
        return ApiResponse.success(consultingDetailForm);
    }

}
