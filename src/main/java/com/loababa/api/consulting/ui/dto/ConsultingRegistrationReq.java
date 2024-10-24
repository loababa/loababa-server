package com.loababa.api.consulting.ui.dto;

import com.loababa.api.consulting.domain.impl.model.ConsultingPost;
import com.loababa.api.consulting.domain.impl.model.LossamSchedule;
import com.loababa.api.consulting.domain.impl.model.TimeRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.loababa.api.common.constant.ValidationRegex.ISO_LOCAL_TIME;


@Schema(description = "로쌤 상담 정보 작성")
public record ConsultingRegistrationReq(
        @NotBlank(message = "로쌤 한줄소개는 공백일 수 없습니다.")
        @Length(min = 1, max = 200, message = "로쌤 한줄소개는 1자 이상, 최대 200자까지 가능합니다.")
        String title,

        @Length(min = 1, max = 500, message = "로쌤 한줄소개는 1자 이상, 최대 700자까지 가능합니다.")
        String contents,

        @Size(min = 1, max = 6)
        List<@NotBlank(message = "대화 가능한 주제는 공백일 수 없습니다.")
        @Length(max = 6, message = "대화 가능한 주제는 최대 6글자까지 가능합니다.") String> topics,

        @Valid WeeklyScheduleReq weekly,
        @Valid DailyScheduleReq daily
) {

    public ConsultingPost toLossamConsultingPost() {
        return new ConsultingPost(title, contents, topics);
    }

    public LossamSchedule toLossamSchedule() {
        if (weekly != null) {
            return weekly.toWeeklySchedule();
        }
        if (daily != null) {
            return daily.toDailySchedule();
        }

        throw new ConstraintViolationException("잘못된 스케쥴 입력입니다.", Collections.emptySet());
    }

    public record WeeklyScheduleReq(
            @Pattern(regexp = ISO_LOCAL_TIME)
            String weekdayStartTime,
            @Pattern(regexp = ISO_LOCAL_TIME)
            String weekdayEndTime,
            @Pattern(regexp = ISO_LOCAL_TIME)
            String weekendStartTime,
            @Pattern(regexp = ISO_LOCAL_TIME)
            String weekendEndTime
    ) {
        public LossamSchedule toWeeklySchedule() {
            TimeRange weekday = new TimeRange(
                    LocalTime.parse(weekdayStartTime),
                    LocalTime.parse(weekdayEndTime)
            );
            TimeRange weekend = new TimeRange(
                    LocalTime.parse(weekendStartTime),
                    LocalTime.parse(weekendEndTime)
            );

            Map<DayOfWeek, TimeRange> scheduleMap = Map.of(
                    DayOfWeek.MONDAY, weekday,
                    DayOfWeek.TUESDAY, weekday,
                    DayOfWeek.WEDNESDAY, weekday,
                    DayOfWeek.THURSDAY, weekday,
                    DayOfWeek.FRIDAY, weekday,
                    DayOfWeek.SATURDAY, weekend,
                    DayOfWeek.SUNDAY, weekend
            );
            return new LossamSchedule(scheduleMap);
        }
    }

    public record DailyScheduleReq(
            @Pattern(regexp = ISO_LOCAL_TIME) String mondayStartTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String mondayEndTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String tuesdayStartTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String tuesdayEndTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String wednesdayStartTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String wednesdayEndTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String thursdayStartTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String thursdayEndTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String fridayStartTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String fridayEndTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String saturdayStartTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String saturdayEndTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String sundayStartTime,
            @Pattern(regexp = ISO_LOCAL_TIME) String sundayEndTime
    ) {
        public LossamSchedule toDailySchedule() {
            Map<DayOfWeek, TimeRange> scheduleMap = Map.of(
                    DayOfWeek.MONDAY, new TimeRange(
                            LocalTime.parse(mondayStartTime),
                            LocalTime.parse(mondayEndTime)
                    ),
                    DayOfWeek.TUESDAY, new TimeRange(
                            LocalTime.parse(tuesdayStartTime),
                            LocalTime.parse(tuesdayEndTime)
                    ),
                    DayOfWeek.WEDNESDAY, new TimeRange(
                            LocalTime.parse(wednesdayStartTime),
                            LocalTime.parse(wednesdayEndTime)
                    ),
                    DayOfWeek.THURSDAY, new TimeRange(
                            LocalTime.parse(thursdayStartTime),
                            LocalTime.parse(thursdayEndTime)
                    ),
                    DayOfWeek.FRIDAY, new TimeRange(
                            LocalTime.parse(fridayStartTime),
                            LocalTime.parse(fridayEndTime)
                    ),
                    DayOfWeek.SATURDAY, new TimeRange(
                            LocalTime.parse(saturdayStartTime),
                            LocalTime.parse(saturdayEndTime)
                    ),
                    DayOfWeek.SUNDAY, new TimeRange(
                            LocalTime.parse(sundayStartTime),
                            LocalTime.parse(sundayEndTime)
                    )
            );
            return new LossamSchedule(scheduleMap);
        }
    }

}
