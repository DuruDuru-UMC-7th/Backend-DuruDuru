package com.backend.DuruDuru.global.service.IngredientService;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;

public interface IngredientCommandService {

    Ingredient createRawIngredient(Long memberId, IngredientRequestDTO.CreateRawIngredientDTO request);
    Ingredient updateIngredient(Long memberId, Long ingredientId, IngredientRequestDTO.UpdateIngredientDTO request);
    void deleteIngredient(Long memberId, Long ingredientId);

    Ingredient registerPurchaseDate(Long memberId, Long ingredientId, IngredientRequestDTO.PurchaseDateRequestDTO request);
    Ingredient registerExpiryDate(Long memberId, Long ingredientId, IngredientRequestDTO.ExpiryDateRequestDTO request);
    Ingredient setStorageType(Long memberId, Long ingredientId, IngredientRequestDTO.StorageTypeRequestDTO request);

    Ingredient registerIngredientImage(Long memberId, Long ingredientId, IngredientRequestDTO.IngredientImageRequestDTO request);
    Ingredient setCategory(Long memberId, Long ingredientId, IngredientRequestDTO.SetCategoryRequestDTO request);
}
