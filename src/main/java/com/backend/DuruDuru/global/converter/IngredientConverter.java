package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Receipt;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public static IngredientResponseDTO.IngredientDetailDTO toIngredientDetailDTO(Ingredient ingredient) {
        return IngredientResponseDTO.IngredientDetailDTO.builder()
                .memberId(ingredient.getMember().getMemberId())
                .fridgeId(ingredient.getFridge().getFridgeId())
                .ingredientId(ingredient.getIngredientId())
                .ingredientName(ingredient.getIngredientName())
                .count(ingredient.getCount())
                .purchaseDate(ingredient.getPurchaseDate())
                .expiryDate(ingredient.getExpiryDate())
                .storageType(String.valueOf(ingredient.getStorageType()))
                .majorCategory(ingredient.getMajorCategory().name())
                .minorCategory(ingredient.getMinorCategory().name())
                .ingredientImageUrl(ingredient.getIngredientImg().getIngredientImgUrl())
                .createdAt(ingredient.getCreatedAt())
                .updatedAt(ingredient.getUpdatedAt())
                .build();
    }

    public static IngredientResponseDTO.IngredientDetailListDTO toIngredientDetailListDTO(List<Ingredient> ingredients) {
        List<IngredientResponseDTO.IngredientDetailDTO> ingredientDetailDTOList = ingredients.stream()
                .map(IngredientConverter::toIngredientDetailDTO)
                .collect(Collectors.toList());

        return IngredientResponseDTO.IngredientDetailListDTO.builder()
                .ingredients(ingredientDetailDTOList)
                .count(ingredients.size())
                .build();
    }

    public static IngredientResponseDTO.PurchaseDateResultDTO toPurchaseDateResultDTO(Ingredient ingredient) {
        return IngredientResponseDTO.PurchaseDateResultDTO.builder()
                .memberId(ingredient.getMember().getMemberId())
                .ingredientId(ingredient.getIngredientId())
                .ingredientName(ingredient.getIngredientName())
                .fridgeId(ingredient.getFridge().getFridgeId())
                .purchaseDate(ingredient.getPurchaseDate())
                .build();
    }

    public static IngredientResponseDTO.StorageTypeResultDTO toStorageTypeResultDTO(Ingredient ingredient) {
        return IngredientResponseDTO.StorageTypeResultDTO.builder()
                .memberId(ingredient.getMember().getMemberId())
                .ingredientId(ingredient.getIngredientId())
                .ingredientName(ingredient.getIngredientName())
                .fridgeId(ingredient.getFridge().getFridgeId())
                .storageType(String.valueOf(ingredient.getStorageType()))
                .build();
    }


    public static IngredientResponseDTO.IngredientImageDTO toIngredientImageDTO(Ingredient ingredient) {
        return IngredientResponseDTO.IngredientImageDTO.builder()
                .memberId(ingredient.getMember().getMemberId())
                .fridgeId(ingredient.getFridge().getFridgeId())
                .ingredientId(ingredient.getIngredientId())
                .ingredientName(ingredient.getIngredientName())
                .ingredientImageUrl(ingredient.getIngredientImg().getIngredientImgUrl())
                .build();
    }


    public static IngredientResponseDTO.SetCategoryResultDTO toSetCategoryResultDTO(Ingredient ingredient) {
        return IngredientResponseDTO.SetCategoryResultDTO.builder()
                .memberId(ingredient.getMember().getMemberId())
                .fridgeId(ingredient.getFridge().getFridgeId())
                .ingredientId(ingredient.getIngredientId())
                .ingredientName(ingredient.getIngredientName())
                .majorCategory(ingredient.getMajorCategory().name()) // Enum to String
                .minorCategory(ingredient.getMinorCategory().name()) // Enum to String
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


    public static IngredientResponseDTO.CreateOCRIngredientResultDTO toCreateOCRResultDTO(Ingredient ingredient) {
        return IngredientResponseDTO.CreateOCRIngredientResultDTO.builder()
                .memberId(ingredient.getMember().getMemberId())
                .receiptId(ingredient.getReceipt().getReceiptId())
                .fridgeId(ingredient.getFridge().getFridgeId())
                .ingredientId(ingredient.getIngredientId())
                .ingredientName(ingredient.getIngredientName())
                .count(ingredient.getCount())
                .majorCategory(ingredient.getMajorCategory().name())
                .minorCategory(ingredient.getMinorCategory().name())
                .build();
    }

    public static IngredientResponseDTO.IngredientOCRDetailListDTO toIngredientOCRDetailListDTO(List<Ingredient> ingredients) {
        LocalDate purchaseDate = ingredients.isEmpty() ? null : ingredients.get(0).getPurchaseDate();

        List<IngredientResponseDTO.CreateOCRIngredientResultDTO> ingredientOCRDetailDTOList = ingredients.stream()
                .map(IngredientConverter::toCreateOCRResultDTO)
                .collect(Collectors.toList());

        return IngredientResponseDTO.IngredientOCRDetailListDTO.builder()
                .purchaseDate(purchaseDate)
                .ingredients(ingredientOCRDetailDTOList)
                .build();
    }

    public static IngredientResponseDTO.UpdateOCRIngredientResultDTO UpdateOCRIngredientResultDTO(Ingredient ingredient) {
        return IngredientResponseDTO.UpdateOCRIngredientResultDTO.builder()
                .memberId(ingredient.getMember().getMemberId())
                .receiptId(ingredient.getReceipt().getReceiptId())
                .fridgeId(ingredient.getFridge().getFridgeId())
                .ingredientId(ingredient.getIngredientId())
                .ingredientName(ingredient.getIngredientName())
                .count(ingredient.getCount())
                .majorCategory(ingredient.getMajorCategory().name())
                .minorCategory(ingredient.getMinorCategory().name())
                .build();
    }

    public static IngredientResponseDTO.UpdateOCRPurchaseDateResultDTO toOCRPurchaseDateResultDTO(Receipt receipt) {
        return IngredientResponseDTO.UpdateOCRPurchaseDateResultDTO.builder()
                .memberId(receipt.getMember().getMemberId())
                .receiptId(receipt.getReceiptId())
                .fridgeId(receipt.getMember().getFridgeId())
                .purchaseDate(receipt.getPurchaseDate())
                .build();
    }

}
