package com.codecool.compiluserrorus.repository;

import com.codecool.compiluserrorus.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> getPostByMemberIdOrderByPostingDateDesc(@Param("userID") long userID);

    List<Post> getPostByOrderByPostingDateDesc();

}