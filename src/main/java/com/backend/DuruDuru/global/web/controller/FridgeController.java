package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.converter.FridgeConverter;
import com.backend.DuruDuru.global.converter.IngredientConverter;
import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.service.FridgeService.FridgeQueryService;
import com.backend.DuruDuru.global.web.dto.Fridge.FridgeResponseDTO;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;
import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/{member_id}/all/recent")
    @Operation(summary = "최신 등록된 순으로 전체 식재료 리스트 조회 API", description = "사용자의 냉장고에 최신 등록된 순으로 전체 식재료 리스트를 조회하는 API 입니다.")
    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> findAllRecentIngredients(@PathVariable("member_id") Long memberId) {
        List<Ingredient> ingredients = fridgeQueryService.getAllIngredients(memberId);
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_OK, FridgeConverter.toIngredientDetailListDTO(ingredients));
    }

    // 소비기한 임박순 식재료 리스트 조회
    @GetMapping("/{member_id}/near-expiry")
    @Operation(summary = "식재료 소비기한 임박순 리스트 조회 API", description = "식재료 리스트를 소비기한이 임박한 순서대로 조회하는 API 입니다.")
    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> ingredientNearExpiry(@PathVariable("member_id") Long memberId) {
        List<Ingredient> ingredients = fridgeQueryService.getIngredientsNearExpiry(memberId);
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_OK, FridgeConverter.toIngredientDetailListDTO(ingredients));
    }

    // 소비기한 여유순 식재료 리스트 조회
    @GetMapping("/{member_id}/far-expiry")
    @Operation(summary = "식재료 소비기한 여유순 리스트 조회 API", description = "식재료 리스트를 소비기한이 여유로운 순서대로 조회하는 API 입니다.")
    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> ingredientFarExpiry(@PathVariable("member_id") Long memberId) {
        List<Ingredient> ingredients = fridgeQueryService.getIngredientsFarExpiry(memberId);
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_OK, FridgeConverter.toIngredientDetailListDTO(ingredients));
    }

//    // 식재료 남은 일 수 조회 (5일 이내)
//    @GetMapping("/{member_id}/days-left")
//    @Operation(summary = "식재료 남은 일 수(5일 이내) 조회 API", description = "소비기한이 5일 이내로 남은 식재료의 남은 일 수를 조회하는 API 입니다.")
//    public ApiResponse<FridgeResponseDTO.IngredientDetailListDTO> ingredientLeftDays(@PathVariable("member_id") Long memberId) {
//        List<Ingredient> ingredients = fridgeQueryService.getIngredientsD_day(memberId);
//        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_OK, null);
//    }
}
