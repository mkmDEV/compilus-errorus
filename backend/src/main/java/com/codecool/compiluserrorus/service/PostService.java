package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.repository.PostRepository;
import com.codecool.compiluserrorus.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    @Autowired
    public PostService(PostRepository postRepository, MemberService memberService) {
        this.postRepository = postRepository;
        this.memberService = memberService;
    }

    @Value("${IMAGE_PATH}")
    private String imagePath;

    public List<Post> getOrderedPosts() {
        List<Post> posts = postRepository.getPostByOrderByPostingDateDesc();
        posts.forEach(post -> post.setRomanDate(Util.setRomanDate(post.getPostingDate())));
        return posts;
    }

    public List<Post> getMemberPosts(Member member) {
        Member memberToCheck = memberService.getLoggedInMember(member);
        if (memberToCheck != null) {
            List<Post> posts = postRepository.getPostsByMemberIdOrderByPostingDateDesc(memberToCheck.getId());
            posts.forEach(post -> post.setRomanDate(Util.setRomanDate(post.getPostingDate())));
            return posts;
        }
        return null;
    }

    public Post addPost(Post post, Member member) {
        Member postMember = memberService.getLoggedInMember(member);
        post.setMember(postMember);
        postRepository.save(post);
        return post;
    }

    public Post updatePost(Long id, Post post) {
        Post amendPost = postRepository.findById(id).orElse(null);
        if (amendPost != null) {
            amendPost.setMessage(post.getMessage());
            amendPost.setLikes(post.getLikes());
            amendPost.setDislikes(post.getDislikes());
            amendPost.setImage(post.getImage());
            postRepository.save(amendPost);
        }

        return amendPost;
    }

    public boolean deletePost(Long id) {
        Post postToDelete = postRepository.findById(id).orElse(null);

        if (postToDelete == null) {
            return false;
        }

        if (postToDelete.getImage()!= null) {
            Path path = Paths.get(imagePath + postToDelete.getImage());
            try {
                Files.delete(path);
            } catch (IOException e) {
                System.err.println("Unable to delete image.");
                e.printStackTrace();
            }
        }
        postRepository.findById(id).ifPresent($ -> postRepository.deleteById(id));
        return true;
    }
}
