package com.loababa.api.auth.ui;

import com.loababa.api.auth.domain.auth.impl.model.token.AuthToken;
import com.loababa.api.auth.domain.member.MemberService;
import com.loababa.api.auth.domain.member.impl.model.Member;
import com.loababa.api.auth.ui.dto.LossamSignUpReq;
import com.loababa.api.auth.ui.dto.MemberMyResponse;
import com.loababa.api.common.model.ApiResponse;
import com.loababa.api.common.model.MemberCredential;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
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
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "닉네임 사용 가능"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "닉네임이 존재함",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"code\": \"DUPLICATE_NICKNAME\", \"message\": \"닉네임이 중복됩니다.\"}"
                            )
                    )
            ),
    })
    @GetMapping("/api/v1/lossam/nickname/check")
    public ApiResponse<Void> requestNicknameCheck(
            @Parameter(
                    description = "중복 여부를 확인할 닉네임.",
                    required = true
            )
            @RequestParam
            @Valid @NotBlank @Length(message = "닉네임을 입력해주세요") String nickname
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
                lossamSignUpReq.nickname(),
                lossamSignUpReq.profileImageURL(),
                lossamSignUpReq.toLossamLostArkCharacter()
        );
        return ApiResponse.success(authToken);
    }

    @Operation(description = "내 정보 가져오기", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/api/v1/members/my")
    public ApiResponse<MemberMyResponse> requestMyInfo(MemberCredential memberCredential) {
        Member member = memberService.getMember(memberCredential.memberId());
        return ApiResponse.success(MemberMyResponse.from(member));
    }

}
