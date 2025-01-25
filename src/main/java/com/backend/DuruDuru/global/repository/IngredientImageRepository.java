package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.IngredientImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientImageRepository extends JpaRepository<IngredientImg, Long> {
}
