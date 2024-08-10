package com.loababa.api.consulting.persistence.entity;

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
@Table(name = "consulting_posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultingPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 500)
    private String contents;

    @Column(nullable = false, unique = true)
    private Long memberId;

    public ConsultingPostEntity(String title, String contents, Long memberId) {
        this.title = title;
        this.contents = contents;
        this.memberId = memberId;
    }
}
