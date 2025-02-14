package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.converter.IngredientConverter;
import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Receipt;
import com.backend.DuruDuru.global.security.handler.annotation.AuthUser;
import com.backend.DuruDuru.global.service.OCRService.ClovaOCRReceiptService;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/OCR")
@Tag(name = "OCR API", description = "영수증 인식을 위한 OCR API입니다.")
public class OCRController {

    private final ClovaOCRReceiptService clovaOCRReceiptService;

    // 영수증 OCR 처리 및 식재료 저장
    @PostMapping(value = "/receipt", consumes = "multipart/form-data")
    @Operation(summary = "영수증 OCR 처리 및 식재료 저장 API", description = "영수증 이미지를 업로드하여 상품명을 추출하고, 저장된 모든 식재료를 반환합니다.")
    public ApiResponse<IngredientResponseDTO.IngredientDetailListDTO> ingredientOcrAdd(@Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                                          @RequestParam("file") MultipartFile file) throws IOException {
        List<Ingredient> savedIngredients = clovaOCRReceiptService.extractAndCategorizeProductNames(file, member);

        return ApiResponse.onSuccess(SuccessStatus.OCR_CREATE_INGREDIENTS_OK, IngredientConverter.toIngredientDetailListDTO(savedIngredients));
    }

    // OCR 인식된 식재료 이름 및 수량 수정
    @PatchMapping(value = "/ingredient/{ingredient_id}")
    @Operation (summary = "OCR 인식된 식재료 이름 및 수량 수정 API", description = "OCR 인식된 식재료 이름 및 수량을 수정하는 API 입니다.")
    public ApiResponse<IngredientResponseDTO.IngredientDetailDTO> ingredientOcrUpdate(@PathVariable("ingredient_id") Long ingredientId, @RequestParam Long receiptId,
                                                                                               @Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                                               @RequestBody IngredientRequestDTO.UpdateOCRIngredientDTO request) {
        Ingredient updateOCRIngredient = clovaOCRReceiptService.updateOCRIngredient(member, ingredientId, receiptId, request);
        return ApiResponse.onSuccess(SuccessStatus.OCR_UPDATE_INGREDIENT_OK, IngredientConverter.toIngredientDetailDTO(updateOCRIngredient));
    }

    // 영수증 인식 식재료 구매 날짜 수정
    @PatchMapping(value = "/{receipt_id}/purchase-date")
    @Operation(summary = "영수증 인식 식재료 구매 날짜 수정 API", description = "영수증 인식된 식재료의 구매 날짜를 수정하는 API 입니다.")
    public ApiResponse<IngredientResponseDTO.IngredientDetailListDTO> updateOCRPurchaseDate(@PathVariable("receipt_id") Long receiptId,
                                                                                                   @Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                                                   @RequestBody IngredientRequestDTO.PurchaseDateRequestDTO request) {
        List<Ingredient> updatePurchaseDate = clovaOCRReceiptService.updateOCRPurchaseDate(member, receiptId, request);
        return ApiResponse.onSuccess(SuccessStatus.OCR_UPDATE_PURCHASE_DATE_OK, IngredientConverter.toIngredientDetailListDTO(updatePurchaseDate));
    }

}
