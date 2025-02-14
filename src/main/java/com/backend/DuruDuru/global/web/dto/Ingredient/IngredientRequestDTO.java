package com.backend.DuruDuru.global.web.dto.Ingredient;

import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import com.backend.DuruDuru.global.domain.enums.StorageType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IngredientRequestDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRawIngredientDTO {
        private String ingredientName;
        private Long count;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateIngredientDTO {
        private String ingredientName;
        private Long count;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PurchaseDateRequestDTO {
        private LocalDate purchaseDate;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExpiryDateRequestDTO {
        private LocalDate expiryDate;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StorageTypeRequestDTO {
        private StorageType storageType;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IngredientImageRequestDTO {
        private MultipartFile image;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SetCategoryRequestDTO {
        @NotBlank(message = "대분류를 입력해주세요.")
        private String majorCategory;
        @NotBlank(message = "소분류를 입력해주세요.")
        private String minorCategory;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateOCRIngredientDTO {
        @NotBlank(message = "식재료 이름은 필수입니다.")
        private String ingredientName;

        @NotNull(message = "수량은 필수입니다.")
        @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
        private Long count;
    }

}
