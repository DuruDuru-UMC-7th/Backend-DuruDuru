package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/ingredient")
@Tag(name = "식재료 API", description = "식재료 관련 API입니다.")
public class IngredientController {

    // 영수증 이미지 업로드 (식재료 목록 자동 인식)
    @PostMapping("/receipt/upload")
    @Operation(summary = "영수증 이미지 업로드 (식재료 목록 자동 인식) API", description = "영수증 이미지 업로드 (식재료 목록 자동 인식) API")
    public ApiResponse<?> receiptUpload(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 식재료 카테고리 분류 (식재료 목록 자동 인식)
    @PostMapping("/receipt/classify")
    @Operation(summary = "식재료 카테고리 분류 (식재료 목록 자동 인식) API", description = "식재료 카테고리 분류 (식재료 목록 자동 인식) API")
    public ApiResponse<?> receiptClassify(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 구매한 식재료 목록 조회 (식재료 목록 자동 인식)
    @GetMapping("/purchased")
    @Operation(summary = "구매한 식재료 목록 조회 (식재료 목록 자동 인식) API", description = "구매한 식재료 목록 조회 (식재료 목록 자동 인식) API")
    public ApiResponse<?> purchasedIngredientsList(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 추가할 식재료 사진 등록
    @PostMapping("/photo")
    @Operation(summary = "추가할 식재료 사진 등록 API", description = "추가할 식재료 사진 등록 API")
    public ApiResponse<?> ingredientPhoto(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 식재료 카테고리 설정
    @PostMapping("/category")
    @Operation(summary = "식재료 카테고리 설정 API", description = "식재료 카테고리 설정 API")
    public ApiResponse<?> ingredientCategory(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 식재료 구매 날짜 등록
    @PostMapping("/purchase-date")
    @Operation(summary = "식재료 구매 날짜 등록 API", description = "식재료 구매 날짜 등록 API")
    public ApiResponse<?> purchaseDate(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 식재료 소비기한 등록 (자동 계산 포함)
    @PostMapping("/expiry-date")
    @Operation(summary = "식재료 소비기한 등록 API", description = "식재료 소비기한 등록 API")
    public ApiResponse<?> ingredientExpiryDate(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 식재료 직접 등록
    @PostMapping("/")
    @Operation(summary = "식재료 직접 등록 API", description = "식재료 직접 등록 API")
    public ApiResponse<?> ingredientRawAdd(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 식재료 정보 수정
    @PatchMapping("/{ingredient-id}")
    @Operation(summary = "식재료 정보 수정 API", description = "식재료 정보 수정 API")
    public ApiResponse<?> updateIngredient(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 식재료 삭제
    @DeleteMapping("/{ingredient-id}")
    @Operation(summary = "식재료 삭제 API", description = "식재료 삭제 API")
    public ApiResponse<?> deleteIngredient(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 식재료 이름으로 검색
    @GetMapping("/search/name/list")
    @Operation(summary = "식재료 이름으로 검색 API", description = "식재료 이름으로 검색 API")
    public ApiResponse<?> searchIngredientByName(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 식재료 카테고리로 검색
    @GetMapping("/search/category/list")
    @Operation(summary = "식재료 카테고리로 검색 API", description = "식재료 카테고리로 검색 API")
    public ApiResponse<?> searchIngredientByCategory(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 새로운 식재료 카테고리 추가
    @PostMapping("/category/add")
    @Operation(summary = "새로운 식재료 카테고리 추가 API", description = "새로운 식재료 카테고리 추가 API")
    public ApiResponse<?> addNewCategory(){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }


}