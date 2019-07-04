
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

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    private String url;

    @BeforeEach
    public void init() {
        this.testMember = Member.builder()
                .id(STUB_ID)
                .name("Test Name")
                .email("test@email.com")
                .password("testpass")
                .build();
    }

    @Test
    @Order(1)
    @WithMockUser
    public void testGettingFriendsWithExistingMemberWhenLoggedIn() throws Exception {
        int numberOfFriends = 5;
        Set<Member> friends = MemberTestsUtil.getFriends(numberOfFriends);
        when(this.memberService.getFriends(this.testMember)).thenReturn(friends);

        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(MAIN_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(friends), actualResponseBody);

        verify(this.memberService).getFriends(this.testMember);
        verifyZeroInteractions(this.memberService);
    }

    @Test
    @Order(2)
    public void testGettingFriendsWithExistingMemberWhenLoggedOut() throws Exception {
        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        this.mockMvc
                .perform(
                        post(MAIN_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        verifyZeroInteractions(this.memberService);
    }

    @Test
    @Order(3)
    @WithMockUser
    public void testGettingFriendsWithNonExistingMemberWhenLoggedIn() throws Exception {
        when(this.memberService.getFriends(this.testMember)).thenReturn(null);

        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(MAIN_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertTrue(actualResponseBody.isEmpty());

        verify(this.memberService).getFriends(this.testMember);
        verifyZeroInteractions(this.memberService);
    }

    @Test
    @Order(4)
    public void testGettingFriendsWithNonExistingMemberWhenLoggedOut() throws Exception {
        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        this.mockMvc
                .perform(
                        post(MAIN_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isForbidden());

        verifyZeroInteractions(this.memberService);
    }


    @Test
    @Order(5)
    @WithMockUser
    public void getLoggedInMemberWhenLoggedIn() throws Exception {
        when(this.memberService.getLoggedInMember(this.testMember)).thenReturn(this.testMember);

        this.url = MAIN_URL + "/logged-in-member";
        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(this.url)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(this.testMember), actualResponseBody);

        verify(this.memberService).getLoggedInMember(this.testMember);
        verifyZeroInteractions(this.memberService);
    }

    @Test
    @Order(6)
    public void getLoggedInMemberWhenLoggedOut() throws Exception {
        String url = MAIN_URL + "/logged-in-member";
        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        this.mockMvc
                .perform(
                        post(url)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        verifyZeroInteractions(this.memberService);
    }

    @Test
    @Order(7)
    @WithMockUser
    public void testGettingMemberByIdWhenLoggedIn() throws Exception {
        when(this.memberService.getMemberById(this.testMember.getId())).thenReturn(this.testMember);

        this.url = MAIN_URL + "/member";
        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(this.url)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(this.testMember), actualResponseBody);

        verify(this.memberService).getMemberById(this.testMember.getId());
        verifyZeroInteractions(this.memberService);
    }

    @Test
    @Order(8)
    @WithMockUser
    public void testGettingNullMemberByIdWhenLoggedIni() throws Exception {
        when(this.memberService.getMemberById(this.testMember.getId())).thenReturn(null);

        this.url = MAIN_URL + "/member";
        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(this.url)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertTrue(actualResponseBody.isEmpty());

        verify(this.memberService).getMemberById(this.testMember.getId());
        verifyZeroInteractions(this.memberService);
    }


    @Test
    @Order(9)
    public void testGettingMemberWhenLoggedOut() throws Exception {
        String url = MAIN_URL + "/member";
        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        this.mockMvc
                .perform(
                        post(url)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        verifyZeroInteractions(this.memberService);
    }

    @Test
    @Order(10)
    @WithMockUser
    public void addNewFriendToLoggedInMember() throws Exception {
        Set<Member> friends = MemberTestsUtil.getFriends(2);

        Member updatedMember = Member.builder()
                .id(STUB_ID)
                .name("Test Name")
                .email("test@email.com")
                .password("testpass")
                .friends(friends)
                .build();

        when(this.memberService.getMemberById(STUB_ID)).thenReturn(this.testMember);
        when(this.memberService.addFriend(STUB_ID, this.testMember)).thenReturn(updatedMember);

        this.url = MAIN_URL + "/member/{id}";

        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        put(this.url, STUB_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Member actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Member.class);
        assertEquals(actualResponseBody, updatedMember);

        verify(this.memberService).addFriend(STUB_ID, this.testMember);
        verifyZeroInteractions(this.memberService);
    }

    @Test
    @Order(11)
    @WithMockUser
    public void addNotExistingFriendToLoggedInMember() throws Exception {
        when(this.memberService.getMemberById(STUB_ID)).thenReturn(null);
        when(this.memberService.addFriend(STUB_ID, this.testMember)).thenReturn(null);

        this.url = MAIN_URL + "/member/{id}";

        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        put(this.url, STUB_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertTrue(actualResponseBody.isEmpty());

        verify(this.memberService).addFriend(STUB_ID, this.testMember);
        verifyZeroInteractions(this.memberService);
    }

    @Test
    @Order(12)
    public void addFriendWhenUserIsLoggedOut() throws Exception {
        this.url = MAIN_URL + "/member/{id}";
        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        this.mockMvc
                .perform(
                        put(this.url, STUB_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        verifyZeroInteractions(this.memberService);
    }
}