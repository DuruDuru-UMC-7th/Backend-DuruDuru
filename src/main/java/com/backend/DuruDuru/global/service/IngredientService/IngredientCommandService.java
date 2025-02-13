package com.backend.DuruDuru.global.service.IngredientService;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;

public interface IngredientCommandService {

    Ingredient createRawIngredient(Member member, IngredientRequestDTO.CreateRawIngredientDTO request);
    Ingredient updateIngredient(Member member, Long ingredientId, IngredientRequestDTO.UpdateIngredientDTO request);
    void deleteIngredient(Member member, Long ingredientId);

    Ingredient registerPurchaseDate(Member member, Long ingredientId, IngredientRequestDTO.PurchaseDateRequestDTO request);
    Ingredient registerExpiryDate(Member member, Long ingredientId, IngredientRequestDTO.ExpiryDateRequestDTO request);
    Ingredient setStorageType(Member member, Long ingredientId, IngredientRequestDTO.StorageTypeRequestDTO request);

    Ingredient registerIngredientImage(Member member, Long ingredientId, IngredientRequestDTO.IngredientImageRequestDTO request);
    Ingredient setCategory(Member member, Long ingredientId, IngredientRequestDTO.SetCategoryRequestDTO request);
}
