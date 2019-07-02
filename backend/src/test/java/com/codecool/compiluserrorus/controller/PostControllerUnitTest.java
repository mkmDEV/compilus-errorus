package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.service.PostService;
import com.codecool.compiluserrorus.util.PostTestsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerUnitTest {

    private static final int NUMBER_OF_POSTS = 5;
    private static final Long STUB_ID = 1L;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private String url;
    private List<Post> posts;

    @BeforeEach
    public void init() {
        this.posts = PostTestsUtil.getOrderedPosts(NUMBER_OF_POSTS);
    }

    @Test
    @Order(1)
    @WithMockUser
    public void getPostsWhenLoggedIn() throws Exception {
        when(this.postService.getOrderedPosts()).thenReturn(this.posts);

        this.url = "/posts";

        MvcResult mvcResult = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(this.posts), actualResponseBody);

        verify(this.postService).getOrderedPosts();
        verifyNoMoreInteractions(this.postService);

    }

    @Test
    @Order(2)
    public void getPostsWhenLoggedOut() throws Exception {
        when(this.postService.getOrderedPosts()).thenReturn(this.posts);

        this.url = "/posts";

        this.mockMvc.perform(get(url))
                .andExpect(status().isForbidden());

        verifyNoMoreInteractions(this.postService);
    }


//    @Test
//    public void addPost() {
//    }
//
//    @Test
//    public void updatePost() {
//    }
//
//    @Test
//    public void deletePost() {
//    }
}