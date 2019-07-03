package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private Member registeredMember;
    private Member testMember;
    private static final String URL = "/register";

    @BeforeEach
    public void init() {
        testMember = Member.builder()
                .name("Test Name")
                .email("test@email.com")
                .password("testpass")
                .build();

        registeredMember = Member.builder()
                .name("Test Name")
                .email("test@email.com")
                .password("testpass")
                .id(1L)
                .build();
    }


    @Test
    @Order(1)
    public void TestRegistrationWithNewEmail() throws Exception {
        when(this.memberService.register(this.testMember)).thenReturn(this.registeredMember);

        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        MvcResult mvcResult = this.mockMvc.perform(
                post(URL)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(this.registeredMember), actualResponseBody);


        verify(this.memberService).register(this.testMember);
        verifyNoMoreInteractions(this.memberService);
    }

}