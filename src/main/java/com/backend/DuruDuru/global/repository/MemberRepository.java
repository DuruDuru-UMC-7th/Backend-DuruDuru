package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
