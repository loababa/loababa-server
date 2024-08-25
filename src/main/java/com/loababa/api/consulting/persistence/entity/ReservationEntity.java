package com.loababa.api.consulting.persistence.entity;


import com.loababa.api.common.model.BaseEntity;
import com.loababa.api.consulting.constant.ConsultingStatus;
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

import static com.loababa.api.consulting.constant.ConsultingStatus.PENDING;

@Entity
@Getter
@Table(name = "consulting_reservations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long lossamId;

    @Column(nullable = false)
    private long mokokoId;

    @Column(nullable = false)
    private ConsultingStatus consultingStatus = PENDING;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Builder
    private ReservationEntity(long lossamId, long mokokoId) {
        this.lossamId = lossamId;
        this.mokokoId = mokokoId;
    }
}
