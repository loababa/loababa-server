package com.loababa.api.mentoring.domain;

import com.loababa.api.common.MockTestBase;
import com.loababa.api.mentoring.domain.impl.repository.MentoringPostWriter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.loababa.api.mentoring.domain.impl.model.MentoringPostFixtures.newMentoringPost;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

class MentoringServiceTest extends MockTestBase {

    @InjectMocks
    private MentoringService mentoringService;

    @Mock
    private MentoringPostWriter mentoringPostWriter;

    @Test
    void 멘토링_포스트를_등록할_수_있다() {
        // given
        Long memberId = 1L;
        var mentoringPost = newMentoringPost();

        // when
        mentoringService.registerMentoringPost(memberId, mentoringPost);

        // then
        then(mentoringPostWriter).should(times(1)).save(mentoringPost, memberId);
    }

}
