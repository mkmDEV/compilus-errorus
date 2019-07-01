package com.codecool.compiluserrorus.repository;

import com.codecool.compiluserrorus.model.Comment;
import com.codecool.compiluserrorus.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId ORDER BY c.postingDate DESC")
    List<Comment> getCommentsByPostIdOrderByDate(@Param("postId") Long postId);

//    List<Comment> getAllByPostIdOrderByPostingDateDesc(id)

}
