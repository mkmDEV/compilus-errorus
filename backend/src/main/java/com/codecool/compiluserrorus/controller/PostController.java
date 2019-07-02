package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.repository.MemberRepository;
import com.codecool.compiluserrorus.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostController {

    private final PostService postService;
    private final MemberRepository memberRepository;

    @Autowired
    public PostController(PostService postService, MemberRepository memberRepository) {
        this.postService = postService;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public List<Post> getPosts() {
        return postService.getOrderedPosts();
    }

    @GetMapping("/logged-in-member")
    public List<Post> getLoggedInMemberPosts() {
        return postService.getLoggedInMemberPosts(1L);
    }

    @PostMapping
    public Post addPost(@Valid @RequestBody Post post) {
        postService.addPost(post, this.memberRepository.findAll().get(0));
        return post;
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
    }

}