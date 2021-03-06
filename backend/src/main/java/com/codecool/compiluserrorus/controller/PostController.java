package com.codecool.compiluserrorus.controller;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<Post> getPosts() {
        return postService.getOrderedPosts();
    }

    @PostMapping("/logged-in-member")
    public List<Post> getLoggedInMemberPosts(@RequestBody Member member) {
        return postService.getMemberPosts(member);
    }

    @PostMapping("/member-posts")
    public List<Post> getMemberPosts(@RequestBody Member member) {
        return postService.getMemberPosts(member);
    }

    @PostMapping
    public Post addPost(@Valid @RequestBody Post post) {
        return postService.addPost(post, post.getMember());
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
