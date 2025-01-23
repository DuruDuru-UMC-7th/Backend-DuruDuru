package com.backend.DuruDuru.global.web.dto.Ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IngredientResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRawIngredientResultDTO {
        Long ingredientId;
        Long memberId;
        Long fridgeId;
        String ingredientName;
        Long count;
        LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateIngredientResultDTO {
        Long ingredientId;
        Long memberId;
        Long fridgeId;
        String ingredientName;
        Long count;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PurchaseDateResultDTO {
        Long memberId;
        Long fridgeId;
        Long ingredientId;
        String ingredientName;
        LocalDate purchaseDate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StorageTypeResultDTO {
        Long memberId;
        Long fridgeId;
        Long ingredientId;
        String ingredientName;
        String storageType;
    }
}
