package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

}
