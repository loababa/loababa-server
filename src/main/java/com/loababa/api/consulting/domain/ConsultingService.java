package com.loababa.api.consulting.domain;

import com.loababa.api.auth.domain.member.impl.model.LossamBasicInfos.LossamBasicInfo;
import com.loababa.api.auth.domain.member.impl.repository.MemberReader;
import com.loababa.api.consulting.domain.impl.model.ConsultingListForms;
import com.loababa.api.consulting.domain.impl.model.ConsultingPost;
import com.loababa.api.consulting.domain.impl.model.ConsultingRegister;
import com.loababa.api.consulting.domain.impl.model.LossamSchedule;
import com.loababa.api.consulting.domain.impl.model.ConsultingDetailForm;
import com.loababa.api.consulting.domain.impl.repository.ConsultingPostReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultingService {

    private final ConsultingPostReader consultingPostReader;
    private final ConsultingRegister consultingRegister;
    private final MemberReader memberReader;

    public void registerConsulting(Long lossamId, ConsultingPost consultingPost, LossamSchedule lossamSchedule) {
        consultingRegister.register(lossamId, consultingPost, lossamSchedule);
    }

    public ConsultingListForms getAllConsultingListForms() {
        // Lossam 정보 가져오기
        var allLossamInfo = memberReader.findAllLossamInfo();

        // Consulting 정보 가져오기
        var allLossamId = allLossamInfo.getAllLossamId();
        var allConsultingListForm = consultingPostReader.getAllConsultingPostListForm(allLossamId);

        // Lossam 정보를 ID로 매핑
        var lossamInfoMap = allLossamInfo.lossamBasicInfos().stream()
                .collect(Collectors.toMap(LossamBasicInfo::id, info -> info));

        // Consulting 정보를 결합하여 반환
        var consultingListForms = allConsultingListForm.consultingPostListForms().stream()
                .map(postForm -> {
                    var lossamInfo = lossamInfoMap.get(postForm.lossamId());
                    return new ConsultingListForms.ConsultingListForm(
                            postForm.lossamId(),
                            lossamInfo.id(),
                            lossamInfo.profileImageUrl(),
                            lossamInfo.nickname(),
                            lossamInfo.highestLevel(),
                            lossamInfo.classEngravings(),
                            postForm.consultingTitle(),
                            postForm.consultingTopics()
                    );
                })
                .toList();
        return new ConsultingListForms(consultingListForms);
    }

    public ConsultingDetailForm getConsultingDetailForm(Long consultingPostId) {
        return consultingPostReader.readConsultingDetailForm(consultingPostId);
    }

}
