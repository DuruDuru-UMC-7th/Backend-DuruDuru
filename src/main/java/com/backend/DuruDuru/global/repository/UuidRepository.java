package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid, Long> {
}