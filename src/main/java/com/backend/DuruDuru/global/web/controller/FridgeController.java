package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/fridge")
@Tag(name = "냉장고 API", description = "냉장고 관련 API입니다.")
public class FridgeController {

    // 전체 식재료 리스트 조회
    @PostMapping("/ingredient/list")
    @Operation(summary = "등록된 전체 식재료 리스트 조회 API", description = "등록된 전체 식재료 리스트 조회 API")
    public ApiResponse<?> findAllIngredients() {
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_OK, null);
    }

    // 소비기한 임박 식재료 조회
    @PostMapping("/ingredient/near-expiry")
    @Operation(summary = "소비기한 임박 식재료 조회 API", description = "소비기한 임박 식재료 조회 API")
        public ApiResponse<?> ingredientNearExpiry() {
        return ApiResponse.onSuccess(SuccessStatus.FRIDGE_OK, null);
    }

}
