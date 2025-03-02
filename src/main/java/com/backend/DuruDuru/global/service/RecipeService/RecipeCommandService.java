package com.backend.DuruDuru.global.service.RecipeService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.web.dto.Recipe.RecipeResponseDTO;


public interface RecipeCommandService {
    RecipeResponseDTO.RecipeDetailResponse getRecipeDetailByName(Member member, String recipeName);
    void setRecipeFavorite(Member member, String recipeSeq);
    RecipeResponseDTO.RecipePageResponse searchRecipesByIngredient(String ingredients, int page, int size);
    RecipeResponseDTO.RecipePageResponse getFavoriteRecipes(Member member, int page, int size);
    RecipeResponseDTO.RecipePageResponse getPopularRecipes(int page, int size);
    RecipeResponseDTO.RecipePageResponse getRecipesWithIngredientInfo(Member member, int page, int size);
    RecipeResponseDTO.RecipePageResponse searchRecipes(String query, int page, int size);
}