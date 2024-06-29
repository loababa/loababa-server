package com.loababa.api.auth.ui;

import com.loababa.api.auth.domain.MemberService;
import com.loababa.api.common.model.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/v1/lossam/url")
    public ApiResponse<Void> requestLossamSignupURLGeneration() {
        memberService.generateLossamSignupURL();
        return ApiResponse.success();
    }

    @GetMapping("/api/v1/lossam/nickname/check")
    public ApiResponse<Void> requestNicknameCheck(
            @RequestParam String nickname
    ) {
        memberService.validateNickName(nickname);
        return ApiResponse.success();
    }

}
