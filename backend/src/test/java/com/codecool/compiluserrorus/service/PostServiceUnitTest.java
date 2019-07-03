package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.repository.PostRepository;
import com.codecool.compiluserrorus.util.PostTestsUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {PostService.class})
@DataJpaTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostServiceUnitTest {

    private static final Long STUB_ID = 1L;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private MemberService memberService;

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
                .id(STUB_ID)
                .build();

        testPost = Post.builder()
                .message("Test message")
                .postingDate(LocalDateTime.of(2019, 2, 3, 4, 5))
                .likes(10)
                .dislikes(10)
                .member(testMember)
                .build();
    }

    @ParameterizedTest
    @Order(1)
    @ValueSource(ints = {1, 5, 25, 100, 250, 500, 1000})
    public void getOrderedPosts(int posts) {
        this.postList = PostTestsUtil.getOrderedPosts(posts);
        when(this.postRepository.getPostByOrderByPostingDateDesc()).thenReturn(this.postList);
        List<Post> orderedPosts = this.postService.getOrderedPosts();

        assertEquals(this.postList.size(), orderedPosts.size());

        IntStream.range(0, posts - 1)
                .forEach(i -> assertTrue(orderedPosts.get(i).getPostingDate().isAfter(orderedPosts.get(i + 1).getPostingDate())
                ));

        verify(this.postRepository).getPostByOrderByPostingDateDesc();
    }

    @Test
    @Order(2)
    public void getLoggedInMemberPosts() {
        int posts = 5;
        this.postList = PostTestsUtil.getOrderedPosts(posts);

        when(this.memberService.getLoggedInMember(this.testMember)).thenReturn(this.testMember);
        when(this.postRepository.getPostsByMemberIdOrderByPostingDateDesc(STUB_ID)).thenReturn(this.postList);
        List<Post> orderedPosts = this.postService.getLoggedInMemberPosts(this.testMember);

        assertEquals(this.postList.size(), orderedPosts.size());

        IntStream.range(0, posts - 1)
                .forEach(i -> assertEquals(this.postList.get(i).getMember(), orderedPosts.get(i).getMember()));

        verify(this.memberService).getLoggedInMember(this.testMember);
        verify(this.postRepository).getPostsByMemberIdOrderByPostingDateDesc(STUB_ID);
    }

    @Test
    @Order(3)
    public void addValidNewPost() {
        when(this.postRepository.save(testPost)).thenReturn(testPost);
        Post newPost = this.postService.addPost(testPost, testPost.getMember());
        assertFalse(newPost.getMessage().isEmpty());
        verify(this.postRepository).save(testPost);
    }

    @Test
    @Order(4)
    public void addNewPostWithoutMessage() {
        Post wrongPost = Post.builder()
                .message(null)
                .build();

        when(this.postRepository.save(wrongPost)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> this.postService.addPost(wrongPost, testMember));
        verify(this.postRepository).save(wrongPost);
    }

    @Test
    @Order(5)
    public void updateExistingPost() {
        String updatedMessage = "Updated test message";
        int likes = 30;
        int dislikes = 20;

        Post updatedPostData = Post.builder()
                .message(updatedMessage)
                .likes(likes)
                .dislikes(dislikes)
                .build();

        when(this.postRepository.findById(STUB_ID)).thenReturn(Optional.ofNullable(testPost));
        Post updatedPost = this.postService.updatePost(STUB_ID, updatedPostData);

        assertEquals(updatedPost.getMessage(), updatedMessage);
        assertEquals(updatedPost.getLikes(), likes);
        assertEquals(updatedPost.getDislikes(), dislikes);

        verify(this.postRepository).findById(STUB_ID);
    }

    @Test
    @Order(6)
    public void updateNonExistingPost() {
        when(this.postRepository.findById(STUB_ID)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> this.postService.updatePost(STUB_ID, this.testPost));
        verify(this.postRepository).findById(STUB_ID);
    }

    @Test
    @Order(7)
    public void deletePost() {
        when(this.postRepository.findById(STUB_ID)).thenReturn(Optional.ofNullable(testPost));
        assertTrue(() -> this.postService.deletePost(STUB_ID));
        verify(this.postRepository).deleteById(STUB_ID);
    }
}