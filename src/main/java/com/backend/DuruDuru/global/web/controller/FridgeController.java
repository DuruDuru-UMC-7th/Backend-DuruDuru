package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.converter.FridgeConverter;
import com.backend.DuruDuru.global.converter.IngredientConverter;
import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.security.handler.annotation.AuthUser;
import com.backend.DuruDuru.global.service.FridgeService.FridgeQueryService;
import com.backend.DuruDuru.global.web.dto.Fridge.FridgeResponseDTO;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;
import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/fridge")
@Tag(name = "냉장고 API", description = "냉장고 관련 API입니다.")
public class FridgeController {

    private final FridgeQueryService fridgeQueryService;

    // 전체 식재료 리스트 조회 (최신 등록순)
    @GetMapping("/recent")
    @Operation(summary = "최신 등록된 순으로 전체 식재료 리스트 조회 API", description = "사용자의 냉장고에 최신 등록된 순으로 전체 식재료 리스트를 조회하는 API 입니다.")
    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> findAllRecentIngredients(@Parameter(name = "user", hidden = true) @AuthUser Member member) {
        List<Ingredient> ingredients = fridgeQueryService.getAllIngredients(member);
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_GET_RECENT_INGREDIENTS_OK, FridgeConverter.toIngredientDetailListDTO(ingredients));
    }

    // 소비기한 임박순 식재료 리스트 조회
    @GetMapping("/near-expiry")
    @Operation(summary = "식재료 소비기한 임박순 리스트 조회 API", description = "식재료 리스트를 소비기한이 임박한 순서대로 조회하는 API 입니다.")
    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> ingredientsNearExpiry(@Parameter(name = "user", hidden = true) @AuthUser Member member) {
        List<Ingredient> ingredients = fridgeQueryService.getIngredientsNearExpiry(member);
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_GET_NEAR_EXPIRY_INGREDIENTS_OK, FridgeConverter.toIngredientDetailListDTO(ingredients));
    }

    // 소비기한 여유순 식재료 리스트 조회
    @GetMapping("/far-expiry")
    @Operation(summary = "식재료 소비기한 여유순 리스트 조회 API", description = "식재료 리스트를 소비기한이 여유로운 순서대로 조회하는 API 입니다.")
    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> ingredientsFarExpiry(@Parameter(name = "user", hidden = true) @AuthUser Member member) {
        List<Ingredient> ingredients = fridgeQueryService.getIngredientsFarExpiry(member);
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_GET_FAR_EXPIRY_INGREDIENTS_OK, FridgeConverter.toIngredientDetailListDTO(ingredients));
    }

    // 대분류 기준 식재료 최신 등록순 리스트 조회
    @GetMapping("/majorCategory/recent")
    @Operation(summary = "대분류 기준 식재료 최신 등록순 리스트 조회 API", description = "대분류 기준 사용자의 냉장고에 최신 등록된 순으로 전체 식재료 리스트를 조회하는 API 입니다.")
    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> findAllRecentMajorCategoryIngredients(@Parameter(name = "user", hidden = true) @AuthUser Member member, @RequestParam MajorCategory majorCategory) {
        List<Ingredient> ingredients = fridgeQueryService.getAllMajorCategoryIngredients(member, majorCategory);
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_GET_RECENT_INGREDIENTS_BY_MAJOR_OK, FridgeConverter.toIngredientDetailListDTO(ingredients));
    }

    // 대분류 기준 식재료 소비기한 임박순 리스트 조회
    @GetMapping("/majorCategory/near-expiry")
    @Operation(summary = "대분류 기준 식재료 소비기한 임박순 리스트 조회 API", description = "대분류 기준 식재료 리스트를 소비기한이 임박한 순서대로 조회하는 API 입니다.")
    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> majorCategoryIngredientsNearExpiry(@Parameter(name = "user", hidden = true) @AuthUser Member member, @RequestParam MajorCategory majorCategory) {
        List<Ingredient> ingredients = fridgeQueryService.getMajorCategoryIngredientsNearExpiry(member, majorCategory);
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_GET_NEAR_EXPIRY_INGREDIENTS_BY_MAJOR_OK, FridgeConverter.toIngredientDetailListDTO(ingredients));
    }

    // 대분류 기준 식재료 소비기한 여유순 리스트 조회
    @GetMapping("/majorCategory/far-expiry")
    @Operation(summary = "대분류 기준 식재료 소비기한 여유순 리스트 조회 API", description = "대분류 기준 식재료 리스트를 소비기한이 여유로운 순서대로 조회하는 API 입니다.")
    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> majorCategoryIngredientsFarExpiry(@Parameter(name = "user", hidden = true) @AuthUser Member member, @RequestParam MajorCategory majorCategory) {
        List<Ingredient> ingredients = fridgeQueryService.getMajorCategoryIngredientsFarExpiry(member, majorCategory);
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_GET_FAR_EXPIRY_INGREDIENTS_BY_MAJOR_OK, FridgeConverter.toIngredientDetailListDTO(ingredients));
    }

    // 식재료 이름으로 검색 (최신 등록순)
    @GetMapping("/name/recent")
    @Operation(summary = "식재료 이름으로 검색 (최신 등록순) API", description = "식재료 이름으로 검색하여 최신 등록된 순으로 조회하는 API 입니다.")
    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> findByNameRecent(@Parameter(name = "user", hidden = true) @AuthUser Member member, @RequestParam Optional<String> search) {
        List<Ingredient> ingredients = fridgeQueryService.getIngredientsByNameRecent(member, search);
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_GET_RECENT_BY_NAME_OK, FridgeConverter.toIngredientDetailListDTO(ingredients));
    }

    // 식재료 이름으로 검색 (소비기한 임박순)
    @GetMapping("/name/near-expiry")
    @Operation(summary = "식재료 이름으로 검색 (소비기한 임박순) API", description = "식재료 이름으로 검색하여 소비기한이 임박한 순으로 조회하는 API 입니다.")
    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> findByNameNearExpiry(@Parameter(name = "user", hidden = true) @AuthUser Member member, @RequestParam Optional<String> search) {
        List<Ingredient> ingredients = fridgeQueryService.getIngredientsByNameNearExpiry(member, search);
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_GET_NEAR_EXPIRY_BY_NAME_OK, FridgeConverter.toIngredientDetailListDTO(ingredients));
    }

    // 식재료 이름으로 검색 (소비기한 여유순)
    @GetMapping("/name/far-expiry")
    @Operation(summary = "식재료 이름으로 검색 (소비기한 여유순) API", description = "식재료 이름으로 검색하여 소비기한이 여유로운 순으로 조회하는 API 입니다.")
    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> findByNameFarExpiry(@Parameter(name = "user", hidden = true) @AuthUser Member member, @RequestParam Optional<String> search) {
        List<Ingredient> ingredients = fridgeQueryService.getIngredientsByNameFarExpiry(member, search);
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_GET_FAR_EXPIRY_BY_NAME_OK, FridgeConverter.toIngredientDetailListDTO(ingredients));
    }


}
