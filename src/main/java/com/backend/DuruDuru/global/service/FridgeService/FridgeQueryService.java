package com.backend.DuruDuru.global.service.FridgeService;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;

import java.util.List;
import java.util.Optional;

public interface FridgeQueryService {

    List<Ingredient> getAllIngredients(Member member);
    List<Ingredient> getIngredientsNearExpiry(Long memberId);
    List<Ingredient> getIngredientsFarExpiry(Long memberId);

    List<Ingredient> getAllMajorCategoryIngredients(Long memberId, MajorCategory majorCategory);
    List<Ingredient> getMajorCategoryIngredientsNearExpiry(Long memberId, MajorCategory majorCategory);
    List<Ingredient> getMajorCategoryIngredientsFarExpiry(Long memberId, MajorCategory majorCategory);

    List<Ingredient> getIngredientsByNameRecent(Long memberId, Optional<String> optSearch);
    List<Ingredient> getIngredientsByNameNearExpiry(Long memberId, Optional<String> optSearch);
    List<Ingredient> getIngredientsByNameFarExpiry(Long memberId, Optional<String> optSearch);
}
