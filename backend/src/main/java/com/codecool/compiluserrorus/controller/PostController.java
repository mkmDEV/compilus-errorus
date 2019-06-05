package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.repository.PostRepository;
import org.hibernate.annotations.SQLDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpResponse;
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
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        Post amendPost = postRepository.findById(id).orElse(null);
        assert amendPost != null;
        amendPost.setMessage(post.getMessage());
        amendPost.setLikes(post.getLikes());
        amendPost.setDislikes(post.getDislikes());
        amendPost.setImage(post.getImage());

        postRepository.save(amendPost);
        return amendPost;
    }

    @DeleteMapping("/{id}")
    public List<Post> deletePost(@PathVariable("id") Long id) {
        Post erasingPost = postRepository.findById(id).orElse(null);
        assert (erasingPost != null);
        postRepository.deleteById(id);

        return postRepository.getPostByOrderByDateDesc();
    }
}