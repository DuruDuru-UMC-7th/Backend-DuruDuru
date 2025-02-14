package com.backend.DuruDuru.global.web.dto.Ingredient;

import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
    public static class IngredientDetailDTO {
        Long memberId;
        Long fridgeId;
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
    public static class ExpiryDateResultDTO {
        Long memberId;
        Long fridgeId;
        Long ingredientId;
        String ingredientName;
        LocalDate purchaseDate;
        LocalDate expiryDate;
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
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class IngredientDetailListDTO {
        private int count;
        private List<IngredientDetailDTO> ingredients;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateOCRIngredientResultDTO {
        private Long memberId;
        private Long receiptId;
        private Long fridgeId;
        private Long ingredientId;
        private String ingredientName;
        private Long count;
        private String majorCategory;
        private String minorCategory;
        private String ingredientImageUrl;
        private String storageType;
        private LocalDate expireDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class IngredientOCRDetailListDTO {
        private LocalDate purchaseDate;
        private List<CreateOCRIngredientResultDTO> ingredients;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateOCRIngredientResultDTO {
        private Long memberId;
        private Long receiptId;
        private Long fridgeId;
        private Long ingredientId;
        private String ingredientName;
        private Long count;
        private String majorCategory;
        private String minorCategory;
        private String ingredientImageUrl;
        private String storageType;
        private LocalDate expireDate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateOCRPurchaseDateResultDTO {
        Long memberId;
        Long fridgeId;
        Long receiptId;
        LocalDate purchaseDate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MinorCategoryDTO {
        private String minorCategory;
        private String majorCategory;
        private String categoryImageUrl;

        private static final String DEFAULT_IMAGE_URL = "https://duruduru.s3.ap-northeast-2.amazonaws.com/76636494-cfa7-4b1b-8649-2eda45f1be8a";

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
