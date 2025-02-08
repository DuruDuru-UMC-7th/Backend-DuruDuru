package com.backend.DuruDuru.global.service.FridgeService;

import com.backend.DuruDuru.global.domain.entity.Ingredient;

import java.util.List;

public interface FridgeQueryService {

    List<Ingredient> getAllIngredients(Long memberId);
    List<Ingredient> getIngredientsNearExpiry(Long memberId);
}
