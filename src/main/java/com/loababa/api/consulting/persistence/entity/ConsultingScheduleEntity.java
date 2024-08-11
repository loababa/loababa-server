package com.loababa.api.consulting.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Table(
        name = "consulting_schedules", uniqueConstraints = {
        @UniqueConstraint(
                name = "member_id_day_of_week_unique_key_index",
                columnNames = {
                        "member_id", "day_of_week"
                }
        )}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultingScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 9)
    private DayOfWeek dayOfWeek;

    @Column(columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(columnDefinition = "TIME")
    private LocalTime endTime;

    @Column(nullable = false)
    private Long memberId;

    public ConsultingScheduleEntity(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, Long memberId) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.memberId = memberId;
    }
}
