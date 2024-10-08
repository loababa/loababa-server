package com.loababa.api.consulting.persistence.entity;

import com.loababa.api.common.model.BaseEntity;
import com.loababa.api.consulting.domain.impl.model.ReservationPreResponses;
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

@Entity
@Getter
@Table(name = "reservation_pre_responses")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationPreResponsesEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String characterDetails;

    @Column(nullable = false)
    private String inquiryDetails;

    @Column(nullable = false)
    private String experience;

    @Column(nullable = false, length = 11)
    private String contactNumber;

    @Column(nullable = false)
    private long memberId;

    @Column(nullable = false, unique = true)
    private long reservationId;

    @Builder
    private ReservationPreResponsesEntity(String characterDetails, String inquiryDetails, String experience, String contactNumber, long memberId, long reservationId) {
        this.characterDetails = characterDetails;
        this.inquiryDetails = inquiryDetails;
        this.experience = experience;
        this.contactNumber = contactNumber;
        this.memberId = memberId;
        this.reservationId = reservationId;
    }

    public ReservationPreResponses toReservationPreResponses() {
        return new ReservationPreResponses(
                id,
                characterDetails,
                inquiryDetails,
                experience,
                contactNumber
        );
    }

}
