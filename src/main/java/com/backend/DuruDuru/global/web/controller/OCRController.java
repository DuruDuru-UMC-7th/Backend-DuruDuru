package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.converter.IngredientConverter;
import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.service.OCRService.ClovaOCRReceiptService;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
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

//    @Operation(summary = "영수증 이미지 업로드", description = "영수증 이미지를 업로드하여 상품명을 추출합니다.")
//    @PostMapping(value = "/receipt", consumes = "multipart/form-data")
//    public ApiResponse<List<String>> processReceipt(@RequestParam("Receipt") MultipartFile file) throws IOException {
//        log.info("Received file: {}", file.getOriginalFilename());
//
//        List<String> productNames = clovaOCRReceiptService.extractProductNames(file);
//
//        log.info("Extracted product names: {}", productNames);
//        return ApiResponse.onSuccess(SuccessStatus.OCR_OK, productNames);
//    }

    @PostMapping(value = "/receipt", consumes = "multipart/form-data")
    @Operation(summary = "영수증 OCR 처리 및 식재료 저장 API", description = "영수증 이미지를 업로드하여 상품명을 추출하고, 저장된 모든 식재료를 반환합니다.")
    public ApiResponse<IngredientResponseDTO.IngredientOCRDetailListDTO> ingredientOcrAdd(@RequestParam Long memberId, @RequestParam("file") MultipartFile file) throws IOException {
        List<Ingredient> savedIngredients = clovaOCRReceiptService.extractAndCategorizeProductNames(file, memberId);

        return ApiResponse.onSuccess(SuccessStatus.OCR_OK, IngredientConverter.toIngredientOCRDetailListDTO(savedIngredients));
    }

    @PatchMapping(value = "/ingredient/{ingredient_id}")
    @Operation (summary = "OCR 인식된 식재료 정보 수정 API", description = "OCR 인식된 식재료 이름 및 수량을 수정하는 API 입니다.")
    public ApiResponse<IngredientResponseDTO.UpdateOCRIngredientResultDTO> ingredientOcrUpdate(@PathVariable("ingredient_id") Long ingredientId, @RequestParam Long memberId, Long receiptId,
                                                                                         @RequestBody IngredientRequestDTO.UpdateOCRIngredientDTO request) {
        Ingredient updateOCRIngredient = clovaOCRReceiptService.updateOCRIngredient(memberId, ingredientId, receiptId, request);
        return ApiResponse.onSuccess(SuccessStatus.OCR_OK, IngredientConverter.UpdateOCRIngredientResultDTO(updateOCRIngredient));
    }

}
