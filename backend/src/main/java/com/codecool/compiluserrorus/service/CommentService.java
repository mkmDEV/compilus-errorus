package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Comment;
import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.repository.CommentRepository;
import com.codecool.compiluserrorus.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getOrderedComments(Long id) {
        List<Comment> comments = commentRepository.getComments(id);

        comments.forEach(comment -> comment.setRomanDate(Util.setRomanDate(comment.getPostingDate())));
        return comments;
    }
}
