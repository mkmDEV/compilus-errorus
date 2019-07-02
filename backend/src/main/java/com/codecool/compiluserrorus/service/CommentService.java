package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Comment;
import com.codecool.compiluserrorus.repository.CommentRepository;
import com.codecool.compiluserrorus.repository.MemberRepository;
import com.codecool.compiluserrorus.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public List<Comment> getCommentsOrderedByDate(Long postId) {
        List<Comment> comments = commentRepository.getCommentsByPostIdOrderByDate(postId);
        comments.forEach(comment -> comment.setRomanDate(Util.setRomanDate(comment.getPostingDate())));
        return comments;
    }

    public void addComment(Comment comment) {
        comment.setMember(memberRepository.findAll().get(0));
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
}
