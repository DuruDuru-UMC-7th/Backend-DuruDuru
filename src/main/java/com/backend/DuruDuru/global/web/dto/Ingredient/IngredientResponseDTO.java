package com.backend.DuruDuru.global.web.dto.Ingredient;

import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public static class IngredientDetailDTO {
        Long memberId;
        Long fridgeId;
        Long receiptId;
        Long ingredientId;
        String ingredientName;
        Long count;
        LocalDate purchaseDate;
        LocalDate expiryDate;
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
        private List<IngredientDetailDTO> ingredients;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MinorCategoryIngredientPreviewListDTO {
        private String minorCategory;
        private int count;
        private List<IngredientDetailDTO> ingredients;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MajorCategoryIngredientPreviewListDTO {
        private String majorCategory;
        private int count;
        private List<IngredientDetailDTO> ingredients;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MinorCategoryDTO {
        private String minorCategory;
        private String majorCategory;
        private String categoryImageUrl;

        private static final String DEFAULT_IMAGE_URL = "https://duruduru.s3.ap-northeast-2.amazonaws.com/files/ea463c2d-fefb-4df5-a10f-d687d14105f8";
        // "https://duruduru.s3.ap-northeast-2.amazonaws.com/76636494-cfa7-4b1b-8649-2eda45f1be8a"

        public static List<MinorCategoryDTO> fromMinorCategories(List<MinorCategory> categories) {
            return categories.stream()
                    .map(category -> new MinorCategoryDTO(
                            category.name(),
                            category.getMajorCategory().name(),
                            DEFAULT_IMAGE_URL
                    ))
                    .collect(Collectors.toList());
        }
    }


}
