package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
