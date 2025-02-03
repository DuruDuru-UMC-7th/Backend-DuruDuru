package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r LEFT JOIN r.memberRecipes mr GROUP BY r ORDER BY COUNT(mr) DESC")
    Page<Recipe> findAllByPopular(Pageable pageable);
}
