package com.loababa.api.consulting.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "consulting_reservation_datetimes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private boolean isConfirmed = false;

    @Column(nullable = false)
    private Long reservationId;

    @Builder
    private ReservationDateTimeEntity(LocalDateTime startDateTime, LocalDateTime endDateTime, Long reservationId) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reservationId = reservationId;
    }

}