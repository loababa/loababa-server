package com.loababa.api.consulting.persistence.entity;

import com.loababa.api.common.model.BaseEntity;
import com.loababa.api.consulting.domain.impl.model.DateTimeRange;
import com.loababa.api.consulting.domain.impl.model.ReservationDateTime;
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
public class ReservationDateTimeEntity extends BaseEntity {

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
    private ReservationDateTimeEntity(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime, Long reservationId) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reservationId = reservationId;
    }

    public ReservationDateTime toReservationDateTime() {
        return new ReservationDateTime(
                id,
                new DateTimeRange(startDateTime, endDateTime)
        );
    }

    public void approved() {
        this.isConfirmed = true;
    }

}
