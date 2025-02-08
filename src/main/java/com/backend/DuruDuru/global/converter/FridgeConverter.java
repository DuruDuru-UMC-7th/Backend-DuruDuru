package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.web.dto.Fridge.FridgeResponseDTO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class FridgeConverter {

    public static FridgeResponseDTO.IngredientDetailDTO toIngredientDetailDTO(Ingredient ingredient) {
        return FridgeResponseDTO.IngredientDetailDTO.builder()
                .memberId(ingredient.getMember().getMemberId())
                .fridgeId(ingredient.getFridge().getFridgeId())
                .ingredientId(ingredient.getIngredientId())
                .ingredientName(ingredient.getIngredientName())
                .count(ingredient.getCount())
                .purchaseDate(ingredient.getPurchaseDate())
                .expiryDate(ingredient.getExpiryDate())
                .dDay(ingredient.getDDay())
                .dDayFormatted(ingredient.getFormattedDDay())
                .storageType(String.valueOf(ingredient.getStorageType()))
                .majorCategory(ingredient.getMajorCategory().name())
                .minorCategory(ingredient.getMinorCategory().name())
                .ingredientImageUrl(ingredient.getIngredientImg() != null ?
                        ingredient.getIngredientImg().getIngredientImgUrl() : "https://duruduru.s3.ap-northeast-2.amazonaws.com/76636494-cfa7-4b1b-8649-2eda45f1be8a")
                .createdAt(ingredient.getCreatedAt())
                .updatedAt(ingredient.getUpdatedAt())
                .build();
    }

    public static FridgeResponseDTO.IngredientDetailListDTO toIngredientDetailListDTO(List<Ingredient> ingredients) {
        List<FridgeResponseDTO.IngredientDetailDTO> ingredientDetailDTOList = ingredients.stream()
                .map(FridgeConverter::toIngredientDetailDTO)
                .collect(Collectors.toList());

        return FridgeResponseDTO.IngredientDetailListDTO.builder()
                .ingredients(ingredientDetailDTOList)
                .count(ingredients.size())
                .build();
    }
}
