package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Comment;
import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getComments(Post post) {
        return commentService.getOrderedComments(post);
    }
}
