package com.loababa.api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loababa.api.auth.domain.auth.impl.model.token.JWTManager;
import com.loababa.api.auth.domain.member.impl.repository.MemberReader;
import com.loababa.api.common.service.impl.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public abstract class ControllerTestBase {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper om;
    @MockBean
    private MessageSender messageSender;
    @MockBean
    private JWTManager jwtManager;
    @MockBean
    private MemberReader memberReader;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

}
