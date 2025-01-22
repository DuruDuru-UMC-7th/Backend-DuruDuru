package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Town;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownRepository extends JpaRepository<Town, Long> {
}
