package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @PostMapping
    public Post addPost(@Valid @RequestBody Post post) {
        postRepository.save(post);
        return post;
    }

}
