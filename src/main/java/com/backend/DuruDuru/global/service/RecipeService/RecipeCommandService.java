package com.backend.DuruDuru.global.service.RecipeService;

import com.backend.DuruDuru.global.web.dto.Recipe.RecipeResponseDTO;

public interface RecipeCommandService {
    RecipeResponseDTO.DetailResponse getRecipeById(Long recipeId);
}
