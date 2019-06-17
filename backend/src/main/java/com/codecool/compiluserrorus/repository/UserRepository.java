package com.codecool.compiluserrorus.repository;

import com.codecool.compiluserrorus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
