package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.repository.PostRepository;
import com.codecool.compiluserrorus.util.PostServiceUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {PostService.class})
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostServiceTest {

    private static final int STUB_USER_ID = 1;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    private Member testMember;
    private Post testPost;
    private List<Post> postList;

    @BeforeEach
    public void init() {
        testMember = Member.builder()
                .name("Test Name")
                .email("test@email.com")
                .password("testpass")
                .build();

        testPost = Post.builder()
                .message("Test message")
                .postingDate(LocalDateTime.of(2019, 2, 3, 4, 5))
                .build();

    }

    @ParameterizedTest
    @Order(1)
    @ValueSource(ints = {1, 5, 25, 100, 250, 500})
    public void getOrderedPosts(int posts) {
        this.postList = PostServiceUtil.getOrderedPosts(posts);
        when(this.postRepository.getPostByOrderByPostingDateDesc()).thenReturn(this.postList);
        List<Post> orderedPosts = this.postService.getOrderedPosts();

        assertEquals(this.postList.size(), orderedPosts.size());

        IntStream.range(0, posts - 1)
                .forEach(i -> assertTrue(orderedPosts.get(i).getPostingDate().isAfter( orderedPosts.get(i + 1).getPostingDate())
                ));

        verify(this.postRepository).getPostByOrderByPostingDateDesc();
    }

    @Test
    @Order(2)
    public void getLoggedInMemberPosts() {
        int posts = 5;

        this.postList = PostServiceUtil.getOrderedPosts(posts);
        System.out.println(this.postList);
        when(this.postRepository.getPostsByMemberIdOrderByPostingDateDesc(STUB_USER_ID)).thenReturn(this.postList);

        List<Post> orderedPosts = this.postService.getLoggedInMemberPosts();

        assertEquals(this.postList.size(), orderedPosts.size());
        verify(this.postRepository).getPostsByMemberIdOrderByPostingDateDesc(STUB_USER_ID);
    }

    @Test
    @Order(3)
    public void addPost() {

    }

    @Test
    public void updatePost() {
    }

    @Test
    public void deletePost() {
    }
}