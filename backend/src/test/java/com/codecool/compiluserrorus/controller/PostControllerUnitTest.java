package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.service.MemberService;
import com.codecool.compiluserrorus.service.PostService;
import com.codecool.compiluserrorus.util.PostTestsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerUnitTest {

    private static final int NUMBER_OF_POSTS = 5;
    private static final Long STUB_ID = 1L;
    private static final String MAIN_URL = "/posts";

    @MockBean
    private PostService postService;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private String url;
    private List<Post> posts;
    private Member testMember;
    private Post testPost;

    private static Stream<Boolean> doesPostExist() {
        return Stream.of(true, false);
    }

    @BeforeEach
    public void init() {
        this.posts = PostTestsUtil.getOrderedPosts(NUMBER_OF_POSTS);

        this.testMember = Member.builder()
                .name("Test Name")
                .email("test@email.com")
                .password("testpass")
                .id(STUB_ID)
                .build();

        this.testPost = Post.builder()
                .message("Test message")
                .postingDate(LocalDateTime.of(2019, 2, 3, 4, 5))
                .likes(10)
                .dislikes(10)
                .member(testMember)
                .build();
    }

    @Test
    @Order(1)
    @WithMockUser
    public void getPostsWhenLoggedIn() throws Exception {
        when(this.postService.getOrderedPosts()).thenReturn(this.posts);

        MvcResult mvcResult = this.mockMvc.perform(get(MAIN_URL))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(actualResponseBody, objectMapper.writeValueAsString(this.posts));

        verify(this.postService).getOrderedPosts();
        verifyNoMoreInteractions(this.postService);
    }

    @Test
    @Order(2)
    public void getPostsWhenLoggedOut() throws Exception {
        this.mockMvc.perform(get(MAIN_URL))
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.postService);
    }

    @Test
    @Order(3)
    @WithMockUser
    public void getLoggedInMemberPostsWhenLoggedIn() throws Exception {
        when(this.memberService.getLoggedInMember(this.testMember)).thenReturn(this.testMember);
        when(this.postService.getLoggedInMemberPosts(this.testMember)).thenReturn(this.posts);

        this.url = MAIN_URL + "/logged-in-member";
        String requestBody = this.objectMapper.writeValueAsString(this.testMember);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(url)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(actualResponseBody, objectMapper.writeValueAsString(this.posts));

        verify(this.postService).getLoggedInMemberPosts(this.testMember);
        verifyNoMoreInteractions(this.postService);
    }

    @Test
    @Order(4)
    public void getLoggedInMemberPostsWhenLoggedOut() throws Exception {
        this.url = MAIN_URL + "/logged-in-member";

        this.mockMvc.perform(get(url))
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.postService);
    }

    @Test
    @Order(5)
    @WithMockUser
    public void addPostWhenLoggedIn() throws Exception {
        when(this.postService.addPost(this.testPost, this.testMember)).thenReturn(this.testPost);

        String requestBody = this.objectMapper.writeValueAsString(this.testPost);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(MAIN_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(actualResponseBody, objectMapper.writeValueAsString(this.testPost));

        verify(this.postService).addPost(this.testPost, this.testMember);
        verifyNoMoreInteractions(this.postService);
    }

    @Test
    @Order(6)
    public void addPostWhenLoggedOut() throws Exception {
        when(this.postService.addPost(this.testPost, this.testMember)).thenReturn(this.testPost);

        String requestBody = this.objectMapper.writeValueAsString(this.testPost);

        this.mockMvc
                .perform(
                        post(MAIN_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.postService);
    }

    @Test
    @Order(7)
    @WithMockUser
    public void updateExistingPostWhenLoggedIn() throws Exception {
        String updatedMessage = "Updated test message";
        int likes = 30;
        int dislikes = 20;

        Post updatedPost = Post.builder()
                .message(updatedMessage)
                .likes(likes)
                .dislikes(dislikes)
                .build();

        when(this.postService.updatePost(STUB_ID, this.testPost)).thenReturn(updatedPost);

        this.url = MAIN_URL + "/{id}";
        String requestBody = this.objectMapper.writeValueAsString(this.testPost);

        MvcResult mvcResult = this.mockMvc.
                perform(
                        put(this.url, STUB_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Post actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Post.class);
        assertEquals(actualResponseBody.getMessage(), updatedPost.getMessage());

        verify(this.postService).updatePost(STUB_ID, this.testPost);
        verifyNoMoreInteractions(this.postService);
    }

    @Test
    @Order(8)
    public void updateExistingPostWhenLoggedOut() throws Exception {
        this.url = MAIN_URL + "/{id}";
        String requestBody = this.objectMapper.writeValueAsString(this.testPost);

        this.mockMvc.
                perform(
                        put(this.url, STUB_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.postService);
    }

    @Test
    @Order(9)
    @WithMockUser
    public void updateNonExistingPostWhenLoggedIn() throws Exception {
        when(this.postService.updatePost(STUB_ID, this.testPost)).thenReturn(null);

        this.url = MAIN_URL + "/{id}";
        String requestBody = this.objectMapper.writeValueAsString(this.testPost);

        MvcResult mvcResult = this.mockMvc.
                perform(
                        put(this.url, STUB_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertTrue(actualResponseBody.isEmpty());

        verify(this.postService).updatePost(STUB_ID, this.testPost);
        verifyNoMoreInteractions(this.postService);
    }


    @ParameterizedTest
    @Order(10)
    @MethodSource("doesPostExist")
    @WithMockUser
    public void deletePostWhenLoggedIn(boolean doesPostExist) throws Exception {
        when(this.postService.deletePost(STUB_ID)).thenReturn(doesPostExist);

        this.url = MAIN_URL + "/{id}";

        MvcResult mvcResult = this.mockMvc
                .perform(
                        delete(this.url, STUB_ID)
                )
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertTrue(actualResponseBody.isEmpty());

        verify(this.postService).deletePost(STUB_ID);
        verifyNoMoreInteractions(this.postService);
    }

    @Test
    @Order(11)
    public void deletePostWhenLoggedOut() throws Exception {
        this.url = MAIN_URL + "/{id}";

        this.mockMvc
                .perform(delete(this.url, STUB_ID))
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.postService);
    }
}