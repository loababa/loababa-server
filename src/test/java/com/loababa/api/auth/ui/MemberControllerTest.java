package com.loababa.api.auth.ui;

import com.loababa.api.auth.domain.member.MemberService;
import com.loababa.api.common.ControllerTestBase;
import com.loababa.api.common.model.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest extends ControllerTestBase {

    @MockBean
    private MemberService memberService;

    @Test
    void 로썜_회원가입_URL_전송_요청을_보낼_수_있다() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/api/v1/lossam/url"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(ApiResponse.success())));
    }

    @Test
    void 닉네임_중복_확인_요청을_보낼_수_있다() throws Exception {
        // given
        String nickname = "nickname";

        // when
        ResultActions resultActions = mvc.perform(
                get("/api/v1/lossam/nickname/check")
                        .queryParam("nickname", nickname)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(ApiResponse.success())));
    }

}
