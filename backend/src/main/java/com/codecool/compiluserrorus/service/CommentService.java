package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Comment;
import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.repository.CommentRepository;
import com.codecool.compiluserrorus.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;

    @Autowired
    public CommentService(CommentRepository commentRepository, MemberService memberService) {
        this.commentRepository = commentRepository;
        this.memberService = memberService;
    }

    public List<Comment> getCommentsOrderedByDate(Long postId) {
        List<Comment> comments = commentRepository.getCommentsByPostIdOrderByDate(postId);
        comments.forEach(comment -> comment.setRomanDate(Util.setRomanDate(comment.getPostingDate())));
        return comments;
    }

    public Comment addComment(Comment comment, Member member) {
        Member commentingMember = memberService.getLoggedInMember(member);
        comment.setMember(commentingMember);
        commentRepository.save(comment);
        return comment;
    }

    public Comment updateComment(Long id, Comment comment) {
        Comment amendComment = commentRepository.findById(id).orElse(null);
        if (amendComment != null) {
            amendComment.setMessage(comment.getMessage());
            amendComment.setLikes(comment.getLikes());
            amendComment.setDislikes(comment.getDislikes());
            commentRepository.save(amendComment);
        }
        return amendComment;
    }

    public boolean deleteComment(Long id) {
        Comment toBeDeleted = commentRepository.findById(id).orElse(null);
        if(toBeDeleted == null) return false;
        commentRepository.deleteById(id);
        return true;
    }
}
