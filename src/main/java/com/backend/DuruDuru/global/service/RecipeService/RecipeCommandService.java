package com.backend.DuruDuru.global.service.RecipeService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.web.dto.Recipe.RecipeResponseDTO;

import java.util.List;

public interface RecipeCommandService {
    RecipeResponseDTO.DetailResponse getRecipeById(Long recipeId);
    void setRecipeFavorite(Member member, Long recipeId);
    List<RecipeResponseDTO.DetailResponse> getFavoriteRecipes(Member member);
}
