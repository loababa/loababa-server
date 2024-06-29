package com.loababa.api.auth.infra.persistance.adapter.member;

import com.loababa.api.auth.infra.persistance.repository.MemberJpaRepository;
import com.loababa.api.auth.domain.impl.repository.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberJpaRepositoryAdapter implements MemberReader {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public boolean existNickname(String nickname) {
        return memberJpaRepository.existsByNickname(nickname);
    }

}
