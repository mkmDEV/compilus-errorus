package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Member;
import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.repository.MemberRepository;
import com.codecool.compiluserrorus.repository.PostRepository;
import com.codecool.compiluserrorus.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public PostService(PostRepository postRepository, MemberRepository memberRepository1) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository1;
    }

    public List<Post> getOrderedPosts() {
        List<Post> posts = postRepository.getPostByOrderByPostingDateDesc();
        posts.forEach(post -> post.setRomanDate(Util.setRomanDate(post.getPostingDate())));
        return posts;
    }

    public List<Post> getLoggedInMemberPosts(Long memberId) {
        List<Post> posts = postRepository.getPostsByMemberIdOrderByPostingDateDesc(memberId);
        posts.forEach(post -> post.setRomanDate(Util.setRomanDate(post.getPostingDate())));
        return posts;
    }

    public Post addPost(Post post, Member member) {
        post.setMember(member);
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

    public void deletePost(Long id) {
        postRepository.findById(id).ifPresent(deletablePost -> postRepository.deleteById(id));
    }
}
