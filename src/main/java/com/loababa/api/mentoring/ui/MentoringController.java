package com.loababa.api.mentoring.ui;

import com.loababa.api.common.model.ApiResponse;
import com.loababa.api.mentoring.domain.MentoringService;
import com.loababa.api.mentoring.domain.impl.model.MentoringListForms;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mentoring", description = "상담 관련 API")
@RestController
@RequiredArgsConstructor
public class MentoringController {

    private final MentoringService mentoringService;

    @Operation(description = "멘토링 리스트 불러오기")
    @GetMapping("/api/mentorings")
    public ApiResponse<MentoringListForms> requestMentorings() {
        MentoringListForms allMentoringListForms = mentoringService.getAllMentoringListForms();
        return ApiResponse.success(allMentoringListForms);
    }

}
