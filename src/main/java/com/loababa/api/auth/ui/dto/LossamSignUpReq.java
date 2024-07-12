package com.loababa.api.auth.ui.dto;

import com.loababa.api.auth.domain.impl.model.LossamLostArkCharacterInfo;
import com.loababa.api.auth.domain.impl.model.MemberProfile;
import com.loababa.api.auth.domain.impl.model.MentoringPost;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record LossamSignUpReq(
        @Length(max = 8, message = "닉네임은 최대 8글자까지 가능합니다.")
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,
        String profileImageURL,

        @Size(min = 2, max = 2, message = "직업 각인은 두 개만 입력 가능합니다.")
        List<@NotBlank @Length(max = 3) String> classEngravings,
        @NotNull(message = "레벨을 입력해주세요.")
        @Min(value = 1, message = "레벨은 최소 1부터 가능합니다.")
        int highestLevel,

        @NotBlank(message = "로쌤 한줄소개는 공백일 수 없습니다.")
        @Length(min = 1, max = 200, message = "로쌤 한줄소개는 1자 이상, 최대 200자까지 가능합니다.")
        String description,
        @Length(min = 1, max = 500, message = "로쌤 한줄소개는 1자 이상, 최대 700자까지 가능합니다.")
        String selfIntroduce,
        @Size(min = 1, max = 6)
        List<@Length(max = 6) String> topics
) {

    public MemberProfile toLossamProfile() {
        return MemberProfile.createLossamProfile(nickname, profileImageURL);
    }

    public LossamLostArkCharacterInfo toLossamLostArkCharacter() {
        return new LossamLostArkCharacterInfo(highestLevel, classEngravings);
    }

    public MentoringPost toLossamMentoringPost() {
        return new MentoringPost(description, selfIntroduce, topics);
    }

}
