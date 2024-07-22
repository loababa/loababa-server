package com.loababa.api.mentoring.domain;

import com.loababa.api.auth.domain.impl.model.LossamBasicInfos;
import com.loababa.api.auth.domain.impl.repository.MemberReader;
import com.loababa.api.mentoring.domain.impl.model.MentoringDetailForm;
import com.loababa.api.mentoring.domain.impl.model.MentoringListForms;
import com.loababa.api.mentoring.domain.impl.model.MentoringPost;
import com.loababa.api.mentoring.domain.impl.model.MentoringPostListForms;
import com.loababa.api.mentoring.domain.impl.repository.MentoringPostReader;
import com.loababa.api.mentoring.domain.impl.repository.MentoringPostWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentoringService {

    private final MentoringPostReader mentoringPostReader;
    private final MentoringPostWriter mentoringPostWriter;
    private final MemberReader memberReader;

    public void registerMentoringPost(Long memberId, MentoringPost mentoringPost) {
        mentoringPostWriter.save(mentoringPost, memberId);
    }

    public MentoringListForms getAllMentoringListForms() {
        // Lossam 정보 가져오기
        LossamBasicInfos allLossamInfo = memberReader.findAllLossamInfo();

        // Mentoring 정보 가져오기
        List<Long> allLossamId = allLossamInfo.getAllLossamId();
        MentoringPostListForms allMentoringListForm = mentoringPostReader.getAllMentoringPostListForm(allLossamId);

        // Lossam 정보를 ID로 매핑
        Map<Long, LossamBasicInfos.LossamBasicInfo> lossamInfoMap = allLossamInfo.lossamBasicInfos().stream()
                .collect(Collectors.toMap(LossamBasicInfos.LossamBasicInfo::id, info -> info));

        // Mentoring 정보를 결합하여 반환
        List<MentoringListForms.MentoringListForm> mentoringListForms = allMentoringListForm.mentoringPostListForms().stream()
                .map(postForm -> {
                    LossamBasicInfos.LossamBasicInfo lossamInfo = lossamInfoMap.get(postForm.lossamId());
                    return new MentoringListForms.MentoringListForm(
                            postForm.lossamId(),
                            lossamInfo.id(),
                            lossamInfo.profileImageUrl(),
                            lossamInfo.nickname(),
                            lossamInfo.highestLevel(),
                            lossamInfo.classEngravings(),
                            postForm.mentoringTitle(),
                            postForm.mentoringTopics()
                    );
                })
                .toList();
        return new MentoringListForms(mentoringListForms);
    }

    public MentoringDetailForm getMentoringDetailForm(Long mentoringPostId) {
        return mentoringPostReader.readMentoringDetailForm(mentoringPostId);
    }

}
