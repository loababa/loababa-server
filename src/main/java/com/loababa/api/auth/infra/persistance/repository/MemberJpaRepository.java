package com.loababa.api.auth.infra.persistance.repository;

import com.loababa.api.auth.exception.MemberClientExceptionInfo;
import com.loababa.api.auth.infra.persistance.dto.LossamBasicInfoDto;
import com.loababa.api.auth.infra.persistance.entity.MemberEntity;
import com.loababa.api.common.exception.LoababaBadRequestException;
import com.loababa.api.common.exception.ServerExceptionInfo;
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

    default MemberEntity getMemberEntityById(Long id) {
        return this.findById(id)
                .orElseThrow(() ->
                        new LoababaBadRequestException(
                                MemberClientExceptionInfo.MEMBER_NOT_FOUND,
                                new ServerExceptionInfo("존재하지 않는 memberId: " + id)
                        )
                );
    }
}
