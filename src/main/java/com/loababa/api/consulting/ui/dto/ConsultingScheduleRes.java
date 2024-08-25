package com.loababa.api.consulting.ui.dto;

import com.loababa.api.consulting.domain.impl.model.TimeRange;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.DayOfWeek;
import java.util.Map;

@Schema(description = "상담 일정 정보")
public record ConsultingScheduleRes(
        @Schema(example = """
                {
                      "MONDAY": {
                        "start": "09:00:00",
                        "end": "12:00:00"
                      },
                      "TUESDAY": {
                        "start": "10:00:00",
                        "end": "13:00:00"
                      },
                      "WEDNESDAY": {
                        "start": "14:00:00",
                        "end": "17:00:00"
                      },
                      "THURSDAY": {
                        "start": "09:00:00",
                        "end": "11:00:00"
                      },
                      "FRIDAY": {
                        "start": "13:00:00",
                        "end": "16:00:00"
                      },
                      "SATURDAY": {
                        "start": "11:00:00",
                        "end": "14:00:00"
                      },
                      "SUNDAY": {
                        "start": "15:00:00",
                        "end": "18:00:00"
                      }
                }
                """)
        Map<DayOfWeek, TimeRange> schedule
) {

}
