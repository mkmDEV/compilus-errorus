package com.codecool.compiluserrorus.repository;

import com.codecool.compiluserrorus.model.Comment;
import com.codecool.compiluserrorus.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);
}
