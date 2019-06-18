package com.codecool.compiluserrorus.repository;

import com.codecool.compiluserrorus.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
