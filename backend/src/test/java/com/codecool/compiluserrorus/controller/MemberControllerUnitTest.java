
package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.service.MemberService;
import com.codecool.compiluserrorus.util.MemberTestsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerUnitTest {

    private static final Long STUB_ID = 1L;
    private static final String MAIN_URL = "/members";

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private Member testMember;

    @BeforeEach
    public void init() {

        this.testMember = Member.builder()
                .name("Test Name")
                .email("test@email.com")
                .password("testpass")
                .id(STUB_ID)
                .build();
    }

    @Test
    @Order(1)
    @WithMockUser
    public void testGettingFriendsWithExistingMemberWhenLoggedIn() throws Exception {
        int numberOfFriends = 5;
        List<Member> friends = MemberTestsUtil.getFriendList(numberOfFriends);
        when(this.memberService.getFriends(this.testMember)).thenReturn(friends);

        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(MAIN_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(friends), actualResponseBody);

        verify(this.memberService).getFriends(this.testMember);
        verifyZeroInteractions(this.memberService);
    }

    @Test
    @Order(2)
    public void testGettingFriendsWithExistingMemberWhenLoggedOut() throws Exception {
        int numberOfFriends = 5;
        List<Member> friends = MemberTestsUtil.getFriendList(numberOfFriends);
        when(this.memberService.getFriends(this.testMember)).thenReturn(friends);

        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        this.mockMvc
                .perform(
                        post(MAIN_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isForbidden());

        verifyZeroInteractions(this.memberService);
    }


}