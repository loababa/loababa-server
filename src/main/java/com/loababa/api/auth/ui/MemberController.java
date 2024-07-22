package com.loababa.api.auth.ui;

import com.loababa.api.auth.domain.MemberService;
import com.loababa.api.auth.domain.impl.model.AuthToken;
import com.loababa.api.auth.ui.dto.LossamSignUpReq;
import com.loababa.api.common.model.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            @Schema(description = "중복 체크할 닉네임")
            @RequestParam String nickname
    ) {
        memberService.validateNickName(nickname);
        return ApiResponse.success();
    }


    @PostMapping("/api/v1/lossam")
    public ApiResponse<AuthToken> requestLossamSignup(
            AuthCredential authCredential,
            @Schema(description = "로쌤 회원 가입 가능 key")
            @RequestParam @NotBlank String key,
            @Schema(description = "로쌤 회원 가입 상세정보")
            @RequestBody @Valid LossamSignUpReq lossamSignUpReq
    ) {
        AuthToken authToken = memberService.signupLossam(
                key,
                authCredential.oauthUserId(),
                lossamSignUpReq.toLossamProfile(),
                lossamSignUpReq.toLossamLostArkCharacter()
        );
        return ApiResponse.success(authToken);
    }

}
