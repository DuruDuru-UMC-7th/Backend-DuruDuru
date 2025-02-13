package com.backend.DuruDuru.global.service.FridgeService;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;

import java.util.List;
import java.util.Optional;

public interface FridgeQueryService {

    List<Ingredient> getAllIngredients(Member member);
    List<Ingredient> getIngredientsNearExpiry(Member member);
    List<Ingredient> getIngredientsFarExpiry(Member member);

    List<Ingredient> getAllMajorCategoryIngredients(Member member, MajorCategory majorCategory);
    List<Ingredient> getMajorCategoryIngredientsNearExpiry(Member member, MajorCategory majorCategory);
    List<Ingredient> getMajorCategoryIngredientsFarExpiry(Member member, MajorCategory majorCategory);

    List<Ingredient> getIngredientsByNameRecent(Member member, Optional<String> optSearch);
    List<Ingredient> getIngredientsByNameNearExpiry(Member member, Optional<String> optSearch);
    List<Ingredient> getIngredientsByNameFarExpiry(Member member, Optional<String> optSearch);
}
