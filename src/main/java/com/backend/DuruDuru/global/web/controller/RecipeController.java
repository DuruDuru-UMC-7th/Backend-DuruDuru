package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.security.handler.annotation.AuthUser;
import com.backend.DuruDuru.global.service.RecipeService.RecipeCommandService;
import com.backend.DuruDuru.global.web.dto.Recipe.RecipeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
@Tag(name = "레시피 API", description = "레시피 관련 API입니다.")
public class RecipeController {

    private final RecipeCommandService recipeService;

    // 특정 레시피 조회
    @GetMapping("/{recipeName}")
    @Parameters({
            @Parameter(name = "recipeName", description = "조회하려는 특정 레시피 이름")
    })
    @Operation(summary = "특정 레시피 조회 API", description = "특정 레시피를 조회하는 API입니다")
    public ApiResponse<RecipeResponseDTO.RecipeDetailResponse> getRecipeByName(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @PathVariable String recipeName
    ){
        RecipeResponseDTO.RecipeDetailResponse response = recipeService.getRecipeDetailByName(member, recipeName);
        return ApiResponse.onSuccess(SuccessStatus.RECIPE_FETCH_OK, response);
    }

    @GetMapping("/search")
    @Operation(summary = "레시피 검색 API", description = "레시피 이름을 기반으로 검색하는 API입니다.")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호, 기본값은 1입니다"),
            @Parameter(name = "size", description = "페이지 당 항목 수, 기본값은 10입니다.")
    })
    public ApiResponse<RecipeResponseDTO.RecipePageResponse> searchRecipes(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        RecipeResponseDTO.RecipePageResponse response = recipeService.searchRecipes(query, page, size);
        return ApiResponse.onSuccess(SuccessStatus.RECIPE_SEARCH_OK, response);
    }

    // 레시피 즐겨찾기 설정
    @PostMapping("/{recipeName}/favorite")
    @Parameters({
            @Parameter(name = "recipeName", description = "즐겨찾기를 설정하려는 레시피 이름")
    })
    @Operation(summary = "레시피 즐겨찾기 추가/삭제 API", description = "레시피 즐겨찾기를 추가하거나 삭제하는 API입니다")
    public ApiResponse<?> setRecipeFavorite(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @RequestParam String recipeName){
        recipeService.setRecipeFavorite(member, recipeName);
        return ApiResponse.onSuccess(SuccessStatus.RECIPE_FAVORITE_SET_OK, null);
    }


    // 내가 즐겨찾기 설정한 레시피 목록 확인
    @GetMapping("/favorite")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호, 기본값은 1입니다"),
            @Parameter(name = "size", description = "페이지 당 항목 수, 기본값은 10입니다.")
    })
    @Operation(summary = "내가 즐겨찾기 설정한 레시피 목록 확인 API", description = "로그인한 사용자가 즐겨찾기 설정한 레시피 목록을 확인하는 API입니다")
    public ApiResponse<RecipeResponseDTO.RecipePageResponse> getFavoriteRecipes(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        RecipeResponseDTO.RecipePageResponse favorites = recipeService.getFavoriteRecipes(member, page, size);
        return ApiResponse.onSuccess(SuccessStatus.RECIPE_FAVORITE_FETCH_OK, favorites);
    }


    // 즐겨찾기 수가 많은 레시피 추천
    @GetMapping("/popular")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호, 기본값은 1입니다"),
            @Parameter(name = "size", description = "페이지 당 항목 수, 기본값은 10입니다.")
    })
    @Operation(summary = "즐겨찾기 수가 많은 레시피 추천 API", description = "즐겨찾기 수가 많은 레시피를 반환하는 API입니다")
    public ApiResponse<RecipeResponseDTO.RecipePageResponse> getPopularRecipes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        RecipeResponseDTO.RecipePageResponse response = recipeService.getPopularRecipes(page, size);
        return ApiResponse.onSuccess(SuccessStatus.RECIPE_FAVORITE_SORT_OK, response);
    }


    // 식재료 목록 기반 레시피 추천
    @GetMapping("/recommend")
    @Parameters({
            @Parameter(name = "ingredients", description = "레시피 추천을 위한 식재료 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 기본값은 1입니다"),
            @Parameter(name = "size", description = "페이지 당 항목 수, 기본값은 10입니다.")
    })
    @Operation(summary = "식재료 목록 기반 레시피 추천 API", description = "보유하고 있는 식재료 목록을 기반으로 레시피를 추천하는 API입니다")
    public ApiResponse<RecipeResponseDTO.RecipePageResponse> recommendRecipesByIngredients(
            @RequestParam String ingredients,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        RecipeResponseDTO.RecipePageResponse recipes = recipeService.searchRecipesByIngredient(ingredients, page, size);
        System.out.println("ingre: " + ingredients);
        return ApiResponse.onSuccess(SuccessStatus.RECIPE_RECOMMEND_OK, recipes);
    }

    @GetMapping("/remaining")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호, 기본값은 1입니다"),
            @Parameter(name = "size", description = "페이지 당 항목 수, 기본값은 10입니다.")
    })
    @Operation(summary = "남은 재료로 뚝딱 한 끼 API", description = "보유하고 있는 식재료로 만들 수 있는 레시피를 추천하는 API입니다. 레시피 이름/사진과 레시피 재료 중 보유하고 있는 식재료와 보유하고 있지 않는 식재료를 반환합니다.")
    public ApiResponse<RecipeResponseDTO.RecipePageResponse> getRecipesWithRemainingIngredients(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        RecipeResponseDTO.RecipePageResponse response = recipeService.getRecipesWithIngredientInfo(member, page, size);
        return ApiResponse.onSuccess(SuccessStatus.RECIPE_REMAINING_OK, response);
    }
}
