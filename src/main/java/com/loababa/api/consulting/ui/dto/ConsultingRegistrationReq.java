package com.loababa.api.consulting.ui.dto;

import com.loababa.api.consulting.domain.impl.model.ConsultingPost;
import com.loababa.api.consulting.domain.impl.model.LossamSchedule;
import com.loababa.api.consulting.domain.impl.model.TimeRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

        WeeklyScheduleReq weekly,
        DailyScheduleReq daily
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

        throw new ConstraintViolationException(
                "잘못된 스케쥴 입력입니다.", Collections.emptySet()
        );
    }

    public record WeeklyScheduleReq(
            String weekdayStartTime,
            String weekdayEndTime,
            String weekendStartTime,
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
            String mondayStartTime, String mondayEndTime,
            String tuesdayStartTime, String tuesdayEndTime,
            String wednesdayStartTime, String wednesdayEndTime,
            String thursdayStartTime, String thursdayEndTime,
            String fridayStartTime, String fridayEndTime,
            String saturdayStartTime, String saturdayEndTime,
            String sundayStartTime, String sundayEndTime
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
