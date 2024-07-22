package com.loababa.api.mentoring.ui;

import com.loababa.api.common.model.AuthCredential;
import com.loababa.api.common.model.ApiResponse;
import com.loababa.api.mentoring.domain.MentoringService;
import com.loababa.api.mentoring.domain.impl.model.MentoringDetailForm;
import com.loababa.api.mentoring.domain.impl.model.MentoringListForms;
import com.loababa.api.mentoring.ui.dto.MentoringPostRegistrationReq;
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

@Tag(name = "Mentoring", description = "상담 관련 API")
@RestController
@RequiredArgsConstructor
public class MentoringController {

    private final MentoringService mentoringService;

    @Operation(description = "멘토링 포스트 등록")
    @PostMapping("/api/mentorings")
    public ApiResponse<Void> requestMentoring(
            AuthCredential credential,
            @Schema(description = "로쌤 멘토링 포스트")
            @RequestBody @Valid MentoringPostRegistrationReq mentoringPostRegistrationReq
    ) {
        mentoringService.registerMentoringPost(
                credential.memberId(),
                mentoringPostRegistrationReq.toLossamMentoringPost()
        );
        return ApiResponse.success();
    }

    @Operation(description = "멘토링 리스트 불러오기")
    @GetMapping("/api/mentorings")
    public ApiResponse<MentoringListForms> requestMentorings(
            AuthCredential authCredential
    ) {
        MentoringListForms allMentoringListForms = mentoringService.getAllMentoringListForms();
        return ApiResponse.success(allMentoringListForms);
    }

    @Operation(description = "멘토링 상세 불러오기")
    @GetMapping("/api/mentorings/{mentoringPostId}")
    public ApiResponse<MentoringDetailForm> requestMentorings(
            AuthCredential authCredential,
            @PathVariable @NotNull Long mentoringPostId
    ) {
        var mentoringDetailForm = mentoringService.getMentoringDetailForm(mentoringPostId);
        return ApiResponse.success(mentoringDetailForm);
    }

}
