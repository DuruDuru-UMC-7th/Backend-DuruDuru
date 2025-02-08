package com.backend.DuruDuru.global.web.dto.Fridge;

import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FridgeResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IngredientDetailDTO {
        Long memberId;
        Long fridgeId;
        Long ingredientId;
        String ingredientName;
        Long count;
        LocalDate purchaseDate;
        LocalDate expiryDate;
        Long dDay;
        String dDayFormatted;
        String storageType;
        String majorCategory;
        String minorCategory;
        String ingredientImageUrl;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class IngredientDetailListDTO {
        private int count;
        private List<FridgeResponseDTO.IngredientDetailDTO> ingredients;
    }

}
