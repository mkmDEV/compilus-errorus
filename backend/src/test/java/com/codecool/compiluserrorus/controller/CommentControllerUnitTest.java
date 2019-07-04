package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Comment;
import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.model.PostType;
import com.codecool.compiluserrorus.service.CommentService;
import com.codecool.compiluserrorus.util.CommentTestUtil;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerUnitTest {

    private static final Long STUB_ID = 1L;
    private static final String MAIN_URL = "/comments";
    private static final String URL_WITH_ID = "/comments/{id}";

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private List<Comment> commentList;
    private Post testPost;
    private Member testMember;
    private Comment testComment;

    @BeforeEach
    public void init() {
        int commentNumber = 10;
        this.commentList = CommentTestUtil.getOrderedComments(commentNumber);

        this.testMember = Member.builder()
                .name("Test Name")
                .email("test@test.hu")
                .password("password")
                .build();

        this.testPost = Post.builder()
                .id(STUB_ID)
                .message("Test post message")
                .postingDate(LocalDateTime.of(2019, 7, 3, 21, 16))
                .likes(10)
                .dislikes(10)
                .postType(PostType.USER)
                .member(this.testMember)
                .build();

        this.testComment = Comment.builder()
                .message("Test comment message")
                .postingDate(LocalDateTime.of(2019, 7, 3, 21, 18))
                .likes(10)
                .dislikes(10)
                .post(this.testPost)
                .member(this.testMember)
                .build();
    }

    @Test
    @Order(1)
    @WithMockUser
    public void testGetCommentsOrderedByDateWhenLoggedIn() throws Exception {
        when(this.commentService.getCommentsOrderedByDate(STUB_ID)).thenReturn(this.commentList);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        get(MAIN_URL)
                                .param("postId", String.valueOf(STUB_ID))
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(this.commentList), responseBody);

        verify(this.commentService).getCommentsOrderedByDate(STUB_ID);
        verifyNoMoreInteractions(this.commentService);
    }

    @Test
    @Order(2)
    public void testGetCommentsOrderedByDateWhenLoggedOut() throws Exception {
        this.mockMvc
                .perform(
                        get(MAIN_URL)
                                .param("postId", String.valueOf(STUB_ID))
                )
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.commentService);
    }

    @Test
    @Order(3)
    @WithMockUser
    public void testAddCommentWhenLoggedIn() throws Exception {
        when(this.commentService.addComment(this.testComment, this.testMember)).thenReturn(this.testComment);

        String requestBody = this.objectMapper.writeValueAsString(testComment);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(MAIN_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(testComment), responseBody);

        verify(this.commentService).addComment(this.testComment, this.testMember);
        verifyNoMoreInteractions(this.commentService);
    }

    @Test
    @Order(4)
    public void testAddCommentWhenLoggedOut() throws Exception {
        String requestBody = this.objectMapper.writeValueAsString(this.testComment);

        this.mockMvc
                .perform(
                        post(MAIN_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.commentService);
    }

    @Test
    @Order(5)
    @WithMockUser
    public void testUpdateCommentWhenLoggedIn() throws Exception {
        Comment updatedComment = Comment.builder()
                .message("Test Updated comment message")
                .postingDate(LocalDateTime.of(2019, 7, 3, 21, 18))
                .likes(20)
                .dislikes(20)
                .post(this.testPost)
                .member(this.testMember)
                .build();

        when(this.commentService.updateComment(STUB_ID, this.testComment)).thenReturn(updatedComment);

        String requestBody = this.objectMapper.writeValueAsString(this.testComment);

        MvcResult mvcResult = this.mockMvc
                .perform(
                        put(URL_WITH_ID, STUB_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(updatedComment), responseBody);

        verify(this.commentService).updateComment(STUB_ID, this.testComment);
        verifyNoMoreInteractions(this.commentService);
    }

    @Test
    @Order(6)
    public void testUpdateCommentWhenLoggedOut() throws Exception {
        String requestBody = this.objectMapper.writeValueAsString(this.testComment);

        this.mockMvc
                .perform(
                        put(URL_WITH_ID, STUB_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.commentService);
    }

    @Test
    @Order(7)
    @WithMockUser
    public void testDeleteCommentWhenLoggedIn() throws Exception {
        when(this.commentService.deleteComment(STUB_ID)).thenReturn(true);

        this.mockMvc
                .perform(
                        delete(URL_WITH_ID, STUB_ID)
                )
                .andExpect(status().isOk());

        verify(this.commentService).deleteComment(STUB_ID);
        verifyNoMoreInteractions(this.commentService);
    }

    @Test
    @Order(8)
    public void testDeleteCommentWhenLoggedOut() throws Exception {
        this.mockMvc
                .perform(
                        delete(URL_WITH_ID, STUB_ID)
                )
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.commentService);
    }

}
