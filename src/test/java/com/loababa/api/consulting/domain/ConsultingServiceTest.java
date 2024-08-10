package com.loababa.api.consulting.domain;

import com.loababa.api.common.MockTestBase;
import com.loababa.api.consulting.domain.impl.repository.ConsultingPostWriter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.loababa.api.consulting.domain.impl.model.ConsultingPostFixtures.newConsultingPost;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

class ConsultingServiceTest extends MockTestBase {

    @InjectMocks
    private ConsultingService consultingService;

    @Mock
    private ConsultingPostWriter consultingPostWriter;

    @Test
    void 멘토링_포스트를_등록할_수_있다() {
        // given
        Long memberId = 1L;
        var consultingPost = newConsultingPost();

        // when
        consultingService.registerConsulting(memberId, consultingPost, lossamSchedule);

        // then
        then(consultingPostWriter).should(times(1)).save(consultingPost, memberId);
    }

}
