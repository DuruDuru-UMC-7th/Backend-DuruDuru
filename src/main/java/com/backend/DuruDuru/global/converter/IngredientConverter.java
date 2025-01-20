package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;

public class IngredientConverter {


    public static Ingredient toIngredient(IngredientRequestDTO.CreateRawIngredientDTO request) {
        return Ingredient.builder()
                .ingredientName(request.getIngredientName())
                .count(request.getCount())
                .build();
    }

    public static IngredientResponseDTO.CreateRawIngredientResultDTO toCreateResultDTO(Ingredient ingredient) {
        return IngredientResponseDTO.CreateRawIngredientResultDTO.builder()
                .ingredientId(ingredient.getIngredientId())
                .memberId(ingredient.getMember().getMemberId())
                .fridgeId(ingredient.getFridge().getFridgeId())
                .ingredientName(ingredient.getIngredientName())
                .count(ingredient.getCount())
                .createdAt(ingredient.getCreatedAt())
                .build();
    }

    public static IngredientResponseDTO.UpdateIngredientResultDTO UpdateIngredientResultDTO(Ingredient ingredient) {
        return IngredientResponseDTO.UpdateIngredientResultDTO.builder()
                .ingredientId(ingredient.getIngredientId())
                .memberId(ingredient.getMember().getMemberId())
                .fridgeId(ingredient.getFridge().getFridgeId())
                .ingredientName(ingredient.getIngredientName())
                .count(ingredient.getCount())
                .createdAt(ingredient.getCreatedAt())
                .updatedAt(ingredient.getUpdatedAt())
                .build();
    }

}
