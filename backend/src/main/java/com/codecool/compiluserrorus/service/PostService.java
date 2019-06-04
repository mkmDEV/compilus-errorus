package com.codecool.compiluserrorus.service;

import com.codecool.compiluserrorus.model.Post;
import com.codecool.compiluserrorus.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getOrderedPosts() {
        return postRepository.getPostByOrderByDateDesc();
    }

    public void addPost(Post post) {
        postRepository.save(post);
    }

    public Post updatePost(Long id, Post post) {
        Post editedPost = postRepository.findById(id).orElse(null);
        assert editedPost != null;
        editedPost.setMessage(post.getMessage());
        editedPost.setLikes(post.getLikes());
        editedPost.setDislikes(post.getDislikes());
        editedPost.setImage(post.getImage());

        postRepository.save(editedPost);
        return editedPost;
    }

    public void deletePost(Long id) {
        Post deletablePost = postRepository.findById(id).orElse(null);
        assert deletablePost != null;
        postRepository.delete(deletablePost);
    }

}
