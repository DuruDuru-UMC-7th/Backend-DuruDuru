package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.IngredientImg;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IngredientImageRepository extends JpaRepository<IngredientImg, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM IngredientImg img WHERE img.ingredient.ingredientId = :ingredientId")
    void deleteByIngredientId(@Param("ingredientId") Long ingredientId);


}
