package com.loababa.api.auth.ui;

import com.loababa.api.auth.domain.member.MemberService;
import com.loababa.api.auth.domain.auth.impl.model.token.AuthToken;
import com.loababa.api.auth.ui.dto.LossamSignUpReq;
import com.loababa.api.common.model.ApiResponse;
import com.loababa.api.common.model.MemberCredential;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(description = "로쌤 회원가입 URL 생성")
    @GetMapping("/api/v1/lossam/url")
    public ApiResponse<Void> requestLossamSignupURLGeneration() {
        memberService.generateLossamSignupURL();
        return ApiResponse.success();
    }

    @Operation(description = "닉네임 중복 체크")
    @GetMapping("/api/v1/lossam/nickname/check")
    public ApiResponse<Void> requestNicknameCheck(
            @Schema(description = "중복 체크할 닉네임")
            @RequestParam String nickname
    ) {
        memberService.validateNickName(nickname);
        return ApiResponse.success();
    }

    @Operation(description = "로쌤 회원 가입", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/api/v1/lossam")
    public ApiResponse<AuthToken> requestLossamSignup(
            MemberCredential memberCredential,
            @Schema(description = "로쌤 회원 가입 가능 key")
            @RequestParam @NotBlank String key,
            @RequestBody @Valid LossamSignUpReq lossamSignUpReq
    ) {
        AuthToken authToken = memberService.signupLossam(
                key,
                memberCredential.oauthUserId(),
                lossamSignUpReq.toLossamProfile(),
                lossamSignUpReq.toLossamLostArkCharacter()
        );
        return ApiResponse.success(authToken);
    }

}
