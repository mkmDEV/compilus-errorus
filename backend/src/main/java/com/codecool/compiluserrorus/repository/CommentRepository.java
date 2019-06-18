package com.codecool.compiluserrorus.repository;

import com.codecool.compiluserrorus.model.Comment;
import com.codecool.compiluserrorus.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.post = :postId")
    List<Comment> getComments(@Param("postId") Long postId);
}
