package com.backend.DuruDuru.global.service.IngredientService;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;

public interface IngredientCommandService {
    // 식재료 직접 추가
    Ingredient createRawIngredient(Member member, IngredientRequestDTO.CreateRawIngredientDTO request);
    // 식재료 정보 수정
    Ingredient updateIngredient(Member member, Long ingredientId, IngredientRequestDTO.UpdateIngredientDTO request);
    // 식재료 삭제
    void deleteIngredient(Member member, Long ingredientId);
    // 식재료 구매날짜 등록
    Ingredient registerPurchaseDate(Member member, Long ingredientId, IngredientRequestDTO.PurchaseDateRequestDTO request);
    // 식재료 소비기한 등록
    Ingredient registerExpiryDate(Member member, Long ingredientId, IngredientRequestDTO.ExpiryDateRequestDTO request);
    // 식재료 보관방법 등록
    Ingredient setStorageType(Member member, Long ingredientId, IngredientRequestDTO.StorageTypeRequestDTO request);
    // 식재료 이미지 등록
    Ingredient registerIngredientImage(Member member, Long ingredientId, IngredientRequestDTO.IngredientImageRequestDTO request);
    // 식재료 카테고리 설정
    Ingredient setCategory(Member member, Long ingredientId, IngredientRequestDTO.SetCategoryRequestDTO request);
}
