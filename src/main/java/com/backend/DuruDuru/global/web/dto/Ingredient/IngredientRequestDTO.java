package com.backend.DuruDuru.global.web.dto.Ingredient;

import lombok.*;

import java.time.LocalDateTime;

public class IngredientRequestDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRawIngredientDTO {
        private String IngredientName;
        private Long count;

    }

}
