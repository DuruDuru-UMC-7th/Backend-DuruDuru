package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.Recipe;
import com.backend.DuruDuru.global.web.dto.Recipe.RecipeResponseDTO;

public class RecipeConverter {

    public static RecipeResponseDTO.RecipeResponse toDetailResponse(Recipe recipe) {
        return RecipeResponseDTO.RecipeResponse.builder()
                .recipeId(recipe.getRecipeId())
                .title(recipe.getTitle())
                .content(recipe.getContent())
                .build();
    }
}
