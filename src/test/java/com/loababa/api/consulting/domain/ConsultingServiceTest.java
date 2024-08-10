package com.loababa.api.consulting.domain;

import com.loababa.api.common.MockTestBase;
import com.loababa.api.consulting.domain.impl.model.ConsultingRegister;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.loababa.api.consulting.domain.impl.model.ConsultingPostFixtures.newConsultingPost;
import static com.loababa.api.consulting.domain.impl.model.LossamScheduleFixtures.newLossamSchedule;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

class ConsultingServiceTest extends MockTestBase {

    @InjectMocks
    private ConsultingService consultingService;

    @Mock
    private ConsultingRegister consultingRegister;

    @Test
    void 로쌤_상담_포스팅를_등록할_수_있다() {
        // given
        Long memberId = 1L;
        var consultingPost = newConsultingPost();
        var lossamSchedule = newLossamSchedule();

        // when
        consultingService.registerConsulting(memberId, consultingPost, lossamSchedule);

        // then
        then(consultingRegister).should(times(1)).register(memberId, consultingPost, lossamSchedule);
    }

}
