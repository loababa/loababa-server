package com.loababa.api.auth.infra.persistance.entity;

import com.loababa.api.auth.domain.member.impl.model.Member;
import com.loababa.api.auth.domain.member.impl.model.MemberProfile;
import com.loababa.api.auth.domain.member.impl.model.MemberType;
import com.loababa.api.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberType memberType;

    @Column(nullable = false)
    private String profileImageUrl;

    @Column(nullable = false, unique = true)
    private Long oAuthUserId;

    @Builder
    private MemberEntity(String nickname, MemberType memberType, String profileImageUrl, Long oAuthUserId) {
        this.nickname = nickname;
        this.memberType = memberType;
        this.profileImageUrl = profileImageUrl;
        this.oAuthUserId = oAuthUserId;
    }

    public Member toMember() {
        return new Member(id, new MemberProfile(nickname, profileImageUrl, memberType));
    }

}
