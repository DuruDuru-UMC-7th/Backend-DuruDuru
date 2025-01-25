package com.backend.DuruDuru.global.web.dto.Ingredient;

import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import lombok.*;

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

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IngredientImageDTO {
        Long memberId;
        Long fridgeId;
        Long ingredientId;
        String ingredientName;
        String ingredientImageUrl;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SetCategoryResultDTO {
        private Long memberId;
        private Long fridgeId;
        private Long ingredientId;
        private String ingredientName;
        private String majorCategory;
        private String minorCategory;
    }


}
