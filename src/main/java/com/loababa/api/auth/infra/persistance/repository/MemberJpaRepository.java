package com.loababa.api.auth.infra.persistance.repository;

import com.loababa.api.auth.infra.persistance.dto.LossamBasicInfoDto;
import com.loababa.api.auth.infra.persistance.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    boolean existsByNickname(String nickname);

    @Query("""
            SELECT m.id
            FROM MemberEntity m
            WHERE m.oAuthUserId = :oAuthUserId
            """)
    Long findIdByOAuthUserId(Long oAuthUserId);


    @Query("""
            SELECT new com.loababa.api.auth.infra.persistance.dto.LossamBasicInfoDto(
                   member.id,
                   member.nickname,
                   member.profileImageUrl,
                   lostArkCharacterInfo.highestLevel,
                   lostArkCharacterInfo.classEngravings
            )
            FROM MemberEntity member
            JOIN LostArkCharacterInfoEntity lostArkCharacterInfo
              ON lostArkCharacterInfo.memberId = member.id
            WHERE member.memberType = 'LOSSAM'
            """)
    List<LossamBasicInfoDto> findAllLossamInfo();
}
