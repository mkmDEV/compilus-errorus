package com.codecool.compiluserrorus.repository;

import com.codecool.compiluserrorus.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
