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
@Table(name = "mentoring_post_topics")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentoringPostTopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6)
    private String topic;

    @Column(nullable = false)
    private Long mentoringPostId;

    public MentoringPostTopicEntity(String topic, Long mentoringPostId) {
        this.topic = topic;
        this.mentoringPostId = mentoringPostId;
    }
}
