package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Comment;
import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.model.PostType;
import com.codecool.compiluserrorus.repository.CommentRepository;
import com.codecool.compiluserrorus.util.CommentTestUtil;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;;

@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {CommentService.class})
@DataJpaTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceUnitTest {

    private static final Long STUB_ID = 1L;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private MemberService memberService;

    @Autowired
    private CommentService commentService;

    private Member testMember;
    private Post testPost;
    private Comment testComment;
    private List<Comment> commentList;

    @BeforeEach
    void init() {
        testMember = Member.builder()
                .name("Test Name")
                .email("test@test.hu")
                .password("password")
                .build();
        testPost = Post.builder()
                .message("Test post message")
                .postingDate(LocalDateTime.of(2019, 7, 3, 21, 16))
                .likes(10)
                .dislikes(10)
                .postType(PostType.USER)
                .member(testMember)
                .build();
        testComment = Comment.builder()
                .message("Test comment message")
                .postingDate(LocalDateTime.of(2019, 7, 3, 21, 18))
                .likes(10)
                .dislikes(10)
                .post(testPost)
                .member(testMember)
                .build();
    }

    @ParameterizedTest
    @Order(1)
    @ValueSource(ints = {1, 5, 25, 100, 250, 500, 1000})
    void testGetCommentsOrderedByDate(int comments) {
        this.commentList = CommentTestUtil.getOrderedComments(comments);
        when(commentRepository.getCommentsByPostIdOrderByDate(STUB_ID)).thenReturn(commentList);
        List<Comment> orderedComments = this.commentService.getCommentsOrderedByDate(STUB_ID);

        assertIterableEquals(commentList, orderedComments);
        verify(this.commentRepository).getCommentsByPostIdOrderByDate(STUB_ID);
    }

    @Order(2)
    @Test
    void testAddComment() {
        Member loggedInMember = Member.builder()
                .email("test@test.hu")
                .build();
        when(this.memberService.getLoggedInMember(loggedInMember)).thenReturn(this.testMember);
        when(this.commentRepository.save(testComment)).thenReturn(testComment);
        Comment newComment = this.commentService.addComment(testComment, loggedInMember);

        assertEquals(testComment, newComment);
        verify(this.memberService).getLoggedInMember(loggedInMember);
        verify(this.commentRepository).save(testComment);
    }

    @Order(3)
    @Test
    void testAddCommentThrowsExceptionWithoutMessage() {
        Member loggedInMember = Member.builder()
                .email("test@test.hu")
                .build();
        Comment commentWithoutMessage = Comment.builder()
                .postingDate(LocalDateTime.of(2019, 7, 3, 21, 18))
                .post(testPost)
                .member(testMember).build();

        when(this.memberService.getLoggedInMember(loggedInMember)).thenReturn(this.testMember);
        when(this.commentRepository.save(commentWithoutMessage)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> {
               this.commentService.addComment(commentWithoutMessage, loggedInMember);
        });
        verify(this.memberService).getLoggedInMember(loggedInMember);
        verify(this.commentRepository).save(commentWithoutMessage);
    }

}

/*
  public void addValidNewPost() {
        when(this.postRepository.save(testPost)).thenReturn(testPost);
        Post newPost = this.postService.addPost(testPost, testPost.getMember());
        assertFalse(newPost.getMessage().isEmpty());
        verify(this.postRepository).save(testPost);
    }

    public void addComment(Comment comment, Member member) {
        Member commentingMember = memberService.getLoggedInMember(member);
        comment.setMember(commentingMember);
        commentRepository.save(comment);
    }

    public Comment updateComment(Long id, Comment comment) {
        Comment amendComment = commentRepository.findById(id).orElse(null);
        if (amendComment != null) {
            amendComment.setMessage(comment.getMessage());
            amendComment.setLikes(comment.getLikes());
            amendComment.setDislikes(comment.getDislikes());
            commentRepository.save(amendComment);
        }
        return comment;
    }

    public void deleteComment(Long id) {
        commentRepository.findById(id).ifPresent(deletableComment -> commentRepository.deleteById(id));
    }
*/
