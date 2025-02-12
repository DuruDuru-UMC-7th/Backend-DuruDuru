package com.backend.DuruDuru.global.service.FridgeService;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;

import java.util.List;

public interface FridgeQueryService {

    List<Ingredient> getAllIngredients(Long memberId);
    List<Ingredient> getIngredientsNearExpiry(Long memberId);
    List<Ingredient> getIngredientsFarExpiry(Long memberId);

    List<Ingredient> getAllMajorCategoryIngredients(Long memberId, MajorCategory majorCategory);
    List<Ingredient> getMajorCategoryIngredientsNearExpiry(Long memberId, MajorCategory majorCategory);
    List<Ingredient> getMajorCategoryIngredientsFarExpiry(Long memberId, MajorCategory majorCategory);

}
