package com.loababa.api.consulting.persistence.entity;


import com.loababa.api.common.model.BaseEntity;
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
@Table(name = "consulting_post_topics")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultingPostTopicEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6)
    private String topic;

    @Column(nullable = false)
    private Long consultingPostId;

    public ConsultingPostTopicEntity(String topic, Long consultingPostId) {
        this.topic = topic;
        this.consultingPostId = consultingPostId;
    }
}
