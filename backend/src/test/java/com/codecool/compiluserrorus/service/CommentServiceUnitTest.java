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
import java.util.Optional;

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
    private Comment updatedCommentData;

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
        updatedCommentData = Comment.builder()
                .message("Updated test comment message")
                .likes(100)
                .dislikes(100)
                .build();
    }

    @ParameterizedTest
    @Order(1)
    @ValueSource(ints = {1, 5, 25, 100, 250, 500, 1000})
    void testGetCommentsOrderedByDate(int comments) {
        List<Comment> commentList = CommentTestUtil.getOrderedComments(comments);
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

    @Order(4)
    @Test
    void testUpdateComment() {
        when(this.commentRepository.findById(STUB_ID)).thenReturn(Optional.ofNullable(testComment));

        Comment updatedComment = this.commentService.updateComment(STUB_ID, updatedCommentData);

        assertEquals(updatedCommentData.getMessage(), updatedComment.getMessage());
        assertEquals(updatedCommentData.getLikes(), updatedComment.getLikes());
        assertEquals(updatedCommentData.getDislikes(), updatedComment.getDislikes());
        verify(this.commentRepository).findById(STUB_ID);
    }

    @Order(4)
    @Test
    void testUpdateNonExistingCommentReturnsNull() {
        when(this.commentRepository.findById(STUB_ID)).thenReturn(Optional.empty());

        Comment updatedComment = this.commentService.updateComment(STUB_ID, updatedCommentData);

        assertNull(updatedComment);
        verify(this.commentRepository).findById(STUB_ID);
    }

    @Order(6)
    @Test
    void testDeleteComment() {
        when(this.commentRepository.findById(STUB_ID)).thenReturn(Optional.ofNullable(testComment));
        assertTrue( () -> this.commentService.deleteComment(STUB_ID));
        verify(this.commentRepository).deleteById(STUB_ID);
    }

    @Order(7)
    @Test
    void testDeleteNonExistingComment() {
        when(this.commentRepository.findById(STUB_ID)).thenReturn(Optional.empty());
        assertFalse( () -> this.commentService.deleteComment(STUB_ID));
    }

}

