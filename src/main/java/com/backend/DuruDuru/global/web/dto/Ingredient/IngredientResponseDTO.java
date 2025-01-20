package com.backend.DuruDuru.global.web.dto.Ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class IngredientResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRawIngredientResultDTO {
//        Long eventId;
//        Long memberId;
//        Long generatorId;
//        String eventTitle;
//        String eventBody;
//        String eventColor;
//        LocalDateTime startDate;
//        LocalDateTime endDate;
        Long ingredientId;
        Long memberId;
        Long fridgeId;
        String ingredientName;
        Long count;
        LocalDateTime createdAt;

    }
}
