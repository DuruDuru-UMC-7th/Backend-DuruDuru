package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
@Tag(name = "레시피 API", description = "레시피 관련 API입니다.")
public class RecipeController {

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

    // 레시피 즐겨찾기 설정
    @PostMapping("/{recipeId}/favorite")
    @Parameters({
            @Parameter(name = "recipeId", description = "즐겨찾기를 설정하려는 레시피 id")
    })
    @Operation(summary = "레시피 즐겨찾기 설정 API", description = "레시피 즐겨찾기를 설정하는 API입니다")
    public ApiResponse<?> setRecipeFavorite(){
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

    // 내가 즐겨찾기 설정한 레시피 목록 확인
    @GetMapping("/bookmark")
    @Operation(summary = "내가 즐겨찾기 설정한 레시피 목록 확인 API", description = "내가 즐겨찾기 설정한 레시피 목록을 확인하는 API입니다")
    public ApiResponse<?> getFavoriteRecipes(){
        return ApiResponse.onSuccess(SuccessStatus.EXAMPLE_OK, null);
    }

    // 특정 레시피 조회
    @GetMapping("/{recipeId}")
    @Parameters({
            @Parameter(name = "recipeId", description = "조회하려는 특정 레시피 id")
    })
    @Operation(summary = "특정 레시피 조회 API", description = "특정 레시피를 조회하는 API입니다")
    public ApiResponse<?> getRecipeById(){
        return ApiResponse.onSuccess(SuccessStatus.EXAMPLE_OK, null);
    }
}
