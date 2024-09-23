package com.loababa.api.auth.ui.dto;

import com.loababa.api.auth.domain.member.impl.model.LossamLostArkCharacterInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Schema(description = "로쌤 회원 가입 상세정보")
public record LossamSignUpReq(
        @Length(min = 2, max = 8, message = "닉네임은 최소 2글자에서 최대 8글자까지 가능합니다.")
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,

        @Schema(nullable = true)
        String profileImageURL,

        @Size(min = 2, max = 2, message = "직업 각인은 두 개만 입력 가능합니다.")
        List<@NotBlank(message = "직업 각인은 공백일 수 없습니다.")
        @Length(max = 3, message = "직업 각인은 최대 3글자만 가능합니다.") String> classEngravings,

        @NotNull(message = "레벨을 입력해주세요.")
        @Min(value = 1, message = "레벨은 최소 1부터 가능합니다.")
        int highestLevel
) {

    public LossamLostArkCharacterInfo toLossamLostArkCharacter() {
        return new LossamLostArkCharacterInfo(highestLevel, classEngravings);
    }

}
