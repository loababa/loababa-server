package com.loababa.api.mentoring.ui.dto;

import com.loababa.api.mentoring.domain.impl.model.MentoringPost;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record MentoringPostRegistrationReq(
        @NotBlank(message = "로쌤 한줄소개는 공백일 수 없습니다.")
        @Length(min = 1, max = 200, message = "로쌤 한줄소개는 1자 이상, 최대 200자까지 가능합니다.")
        String title,

        @Length(min = 1, max = 500, message = "로쌤 한줄소개는 1자 이상, 최대 700자까지 가능합니다.")
        String contents,

        @Size(min = 1, max = 6)
        List<@NotBlank(message = "대화 가능한 주제는 공백일 수 없습니다.")
        @Length(max = 6, message = "대화 가능한 주제는 최대 6글자까지 가능합니다.") String> topics
) {

    public MentoringPost toLossamMentoringPost() {
        return new MentoringPost(title, contents, topics);
    }

}
