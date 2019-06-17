package com.codecool.compiluserrorus.repository;

import com.codecool.compiluserrorus.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Long> {
}
