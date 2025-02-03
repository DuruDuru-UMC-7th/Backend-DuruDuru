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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
@Tag(name = "레시피 API", description = "레시피 관련 API입니다.")
public class RecipeController {

    private final RecipeCommandService recipeService;

    // 특정 레시피 조회
    @GetMapping("/{recipeId}")
    @Parameters({
            @Parameter(name = "recipeId", description = "조회하려는 특정 레시피 id")
    })
    @Operation(summary = "특정 레시피 조회 API", description = "특정 레시피를 조회하는 API입니다")
    public ApiResponse<?> getRecipeById(@PathVariable Long recipeId){
        RecipeResponseDTO.DetailResponse response = recipeService.getRecipeById(recipeId);
        return ApiResponse.onSuccess(SuccessStatus.RECIPE_FETCH_OK, response);
    }

    // 레시피 즐겨찾기 설정
    @PostMapping("/{recipeId}/favorite")
    @Parameters({
            @Parameter(name = "recipeId", description = "즐겨찾기를 설정하려는 레시피 id")
    })
    @Operation(summary = "레시피 즐겨찾기 추가/삭제 API", description = "레시피 즐겨찾기를 추가하거나 삭제하는 API입니다")
    public ApiResponse<?> setRecipeFavorite(@Parameter(name = "user", hidden = true) @AuthUser Member member, @RequestParam Long recipeId){
        recipeService.setRecipeFavorite(member, recipeId);
        return ApiResponse.onSuccess(SuccessStatus.RECIPE_FAVORITE_SET_OK, null);
    }


    // 내가 즐겨찾기 설정한 레시피 목록 확인
    @GetMapping("/favorite")
    @Operation(summary = "내가 즐겨찾기 설정한 레시피 목록 확인 API", description = "로그인한 사용자가 즐겨찾기 설정한 레시피 목록을 확인하는 API입니다")
    public ApiResponse<List<RecipeResponseDTO.DetailResponse>> getFavoriteRecipes(@Parameter(name = "user", hidden = true) @AuthUser Member member){
        List<RecipeResponseDTO.DetailResponse> favorites = recipeService.getFavoriteRecipes(member);
        return ApiResponse.onSuccess(SuccessStatus.RECIPE_FAVORITE_FETCH_OK, favorites);
    }

    // 식재료 목록 기반 레시피 추천
    @GetMapping("/recommend")
    @Operation(summary = "식재료 목록 기반 레시피 추천 API", description = "보유하고 있는 식재료 목록을 기반으로 레시피를 추천하는 API입니다")
    public ApiResponse<?> recommendRecipesByIngredients(){
        return ApiResponse.onSuccess(SuccessStatus.EXAMPLE_OK, null);
    }

    // 즐겨찾기 수가 많은 레시피 추천
    @GetMapping("/popular")
    @Operation(summary = "즐겨찾기 수가 많은 레시피 추천 API", description = "즐겨찾기 수가 많은 레시피를 추천하는 API입니다")
    public ApiResponse<?> recommendPopularRecipes(){
        return ApiResponse.onSuccess(SuccessStatus.EXAMPLE_OK, null);
    }



    // 레시피 공유 (링크, Kakao)
    @PostMapping("/{recipeId}/share")
    @Parameters({
            @Parameter(name = "recipeId", description = "공유하려는 레시피 id")
    })
    @Operation(summary = "레시피 공유 API", description = "레시피 공유(링크, Kakao)를 하는 API입니다")
    public ApiResponse<?> shareRecipes(){
        return ApiResponse.onSuccess(SuccessStatus.EXAMPLE_OK, null);
    }



}
