package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Receipt;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IngredientConverter {

    public static Ingredient toIngredient(IngredientRequestDTO.CreateRawIngredientDTO request) {
        return Ingredient.builder()
                .ingredientName(request.getIngredientName())
                .count(request.getCount())
                .build();
    }

    // 식재료 등록 결과 DTO 변환
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

    // 식재료 상세 정보 DTO 변환
    public static IngredientResponseDTO.IngredientDetailDTO toIngredientDetailDTO(Ingredient ingredient) {
        return IngredientResponseDTO.IngredientDetailDTO.builder()
                .memberId(ingredient.getMember().getMemberId())
                .fridgeId(ingredient.getFridge().getFridgeId())
                .receiptId(Optional.ofNullable(ingredient.getReceipt()).map(Receipt::getReceiptId).orElse(-1L))
                .ingredientId(ingredient.getIngredientId())
                .ingredientName(ingredient.getIngredientName())
                .count(ingredient.getCount())
                .purchaseDate(ingredient.getPurchaseDate())
                .expiryDate(ingredient.getExpiryDate())
                .storageType(String.valueOf(ingredient.getStorageType()))
                .majorCategory(ingredient.getMajorCategory() != null ? ingredient.getMajorCategory().name() : "기타")
                .minorCategory(ingredient.getMinorCategory() != null ? ingredient.getMinorCategory().name() : "기타")
                .ingredientImageUrl(ingredient.getIngredientImg() != null ?
                                ingredient.getIngredientImg().getIngredientImgUrl() : "https://duruduru.s3.ap-northeast-2.amazonaws.com/files/ea463c2d-fefb-4df5-a10f-d687d14105f8")
                .createdAt(ingredient.getCreatedAt())
                .updatedAt(ingredient.getUpdatedAt())
                .build();
    }

    // 식재료 상세 정보 리스트 DTO 변환
    public static IngredientResponseDTO.IngredientDetailListDTO toIngredientDetailListDTO(List<Ingredient> ingredients) {
        List<IngredientResponseDTO.IngredientDetailDTO> ingredientDetailDTOList = ingredients.stream()
                .map(IngredientConverter::toIngredientDetailDTO)
                .collect(Collectors.toList());

        return IngredientResponseDTO.IngredientDetailListDTO.builder()
                .ingredients(ingredientDetailDTOList)
                .count(ingredients.size())
                .build();
    }

    public static IngredientResponseDTO.MinorCategoryIngredientPreviewListDTO toMinorCategoryIngredientPreviewListDTO(
            MinorCategory minorCategory, List<Ingredient> ingredients) {
        return IngredientResponseDTO.MinorCategoryIngredientPreviewListDTO.builder()
                .minorCategory(minorCategory.name())
                .count(ingredients.size())
                .ingredients(ingredients.stream()
                        .map(IngredientConverter::toIngredientDetailDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public static IngredientResponseDTO.MajorCategoryIngredientPreviewListDTO toMajorCategoryIngredientPreviewListDTO(
            MajorCategory majorCategory, List<Ingredient> ingredients) {
        return IngredientResponseDTO.MajorCategoryIngredientPreviewListDTO.builder()
                .majorCategory(majorCategory.name())
                .count(ingredients.size())
                .ingredients(ingredients.stream()
                        .map(IngredientConverter::toIngredientDetailDTO)
                        .collect(Collectors.toList()))
                .build();
    }

}
