package com.backend.DuruDuru.global.service.RecipeService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.web.dto.Recipe.RecipeResponseDTO;

import java.util.List;

public interface RecipeCommandService {
    RecipeResponseDTO.RecipeResponse getRecipeById(Long recipeId);
    void setRecipeFavorite(Member member, Long recipeId);
    List<RecipeResponseDTO.RecipeResponse> getFavoriteRecipes(Member member);
    RecipeResponseDTO.RecipePageResponse getPopularRecipes(int page, int size);
    RecipeResponseDTO.RecipePageResponse searchRecipes(String ingredients, int page, int size);
}
