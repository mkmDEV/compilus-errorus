package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public List<Post> getPosts() {
        return postRepository.getPostByOrderByDateDesc();
    }

    @PostMapping
    public Post addPost(@Valid @RequestBody Post post) {
        postRepository.save(post);
        return post;
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable("id") Long id, @RequestBody Post post) {
        Post amendPost = postRepository.findById(id).orElse(null);
        assert amendPost != null;
        amendPost.setMessage(post.getMessage());
        amendPost.setLikes(post.getLikes());
        amendPost.setDislikes(post.getDislikes());
        amendPost.setImage(post.getImage());

        postRepository.save(amendPost);
        return amendPost;
    }

}