package com.backend.DuruDuru.global.service.RecipeService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.web.dto.Recipe.RecipeResponseDTO;

import java.util.List;

public interface RecipeCommandService {
    RecipeResponseDTO.RecipeDetailResponse getRecipeDetailByName(String recipeName);
    public void setRecipeFavorite(Member member, String recipeSeq);
    RecipeResponseDTO.RecipePageResponse searchRecipes(String ingredients, int page, int size);
    RecipeResponseDTO.RecipePageResponse getFavoriteRecipes(Member member, int page, int size);
    RecipeResponseDTO.RecipePageResponse getPopularRecipes(int page, int size);
}
