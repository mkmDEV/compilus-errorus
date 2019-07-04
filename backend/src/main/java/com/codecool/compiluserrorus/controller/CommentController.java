package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Comment;
import com.codecool.compiluserrorus.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<Comment> getCommentsOrderedByDate(Long postId) {
        return commentService.getCommentsOrderedByDate(postId);
    }

    @PostMapping
    public Comment addComment(@Valid @RequestBody Comment comment) {
        return commentService.addComment(comment, comment.getMember());
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable("id") Long id, @RequestBody Comment comment) {
        return commentService.updateComment(id, comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
    }
}
