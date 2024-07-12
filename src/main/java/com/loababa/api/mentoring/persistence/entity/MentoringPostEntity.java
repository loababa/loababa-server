package com.loababa.api.mentoring.persistence.entity;

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
@Table(name = "mentoring_posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentoringPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false, length = 500)
    private String selfIntroduce;

    @Column(nullable = false, unique = true)
    private Long memberId;

    public MentoringPostEntity(String description, String selfIntroduce, Long memberId) {
        this.description = description;
        this.selfIntroduce = selfIntroduce;
        this.memberId = memberId;
    }
}
