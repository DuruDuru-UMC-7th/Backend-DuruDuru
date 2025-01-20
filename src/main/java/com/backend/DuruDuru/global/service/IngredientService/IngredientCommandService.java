package com.backend.DuruDuru.global.service.IngredientService;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;

public interface IngredientCommandService {

    Ingredient createRawIngredient(Long memberId, Long fridgeId, IngredientRequestDTO.CreateRawIngredientDTO request);
    Ingredient updateIngredient(Long memberId, Long fridgeId, Long ingredientId, IngredientRequestDTO.UpdateIngredientDTO request);

}
