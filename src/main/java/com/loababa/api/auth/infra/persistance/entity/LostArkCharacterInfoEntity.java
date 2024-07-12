package com.loababa.api.auth.infra.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lost_ark_character_infos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LostArkCharacterInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int highestLevel;

    /*
    @Column(nullable = false, length = 6)
    원래 제약 조건, 반정규화로 인해 사용하지 않음
    */
    @Column(nullable = false)
    private String classEngravings;

    @Column(nullable = false, unique = true)
    private Long memberId;

    public LostArkCharacterInfoEntity(int highestLevel, String classEngravings, Long memberId) {
        this.highestLevel = highestLevel;
        this.classEngravings = classEngravings;
        this.memberId = memberId;
    }

}
