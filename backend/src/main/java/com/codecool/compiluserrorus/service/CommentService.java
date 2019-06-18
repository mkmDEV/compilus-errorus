package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Comment;
import com.codecool.compiluserrorus.repository.CommentRepository;
import com.codecool.compiluserrorus.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsOrderedByDate(Long postId) {
        List<Comment> comments = commentRepository.getCommentsByPostIdOrderByDate(postId);
        comments.forEach(comment -> comment.setRomanDate(Util.setRomanDate(comment.getPostingDate())));
        return comments;
    }
}
