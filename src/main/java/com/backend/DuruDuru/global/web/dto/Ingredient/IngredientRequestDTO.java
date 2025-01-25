package com.backend.DuruDuru.global.web.dto.Ingredient;

import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import com.backend.DuruDuru.global.domain.enums.StorageType;
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
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SetCategoryRequestDTO {
        private MinorCategory minorCategory;
    }

}
