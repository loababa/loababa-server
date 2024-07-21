package com.loababa.api.auth.infra.persistance.entity;


import com.loababa.api.auth.domain.impl.model.OAuthPlatform;
import com.loababa.api.auth.domain.impl.model.OAuthUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "oauth_users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"o_auth_id", "o_auth_platform"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long oAuthId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OAuthPlatform oAuthPlatform;

    public OAuthUserEntity(Long oAuthId, OAuthPlatform oAuthPlatform) {
        this.oAuthId = oAuthId;
        this.oAuthPlatform = oAuthPlatform;
    }

    public static OAuthUserEntity from(OAuthUser oAuthUser) {
        return new OAuthUserEntity(oAuthUser.oAuthId(), oAuthUser.oAuthPlatform());
    }

}
