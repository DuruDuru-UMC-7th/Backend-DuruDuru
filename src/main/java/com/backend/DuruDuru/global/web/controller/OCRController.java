package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.service.OCRService.ClovaOCRReceiptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Operation(summary = "영수증 이미지 업로드", description = "영수증 이미지를 업로드하여 상품명을 추출합니다.")
    @PostMapping(value = "/receipt", consumes = "multipart/form-data")
    public ApiResponse<List<String>> processReceipt(@RequestParam("Receipt") MultipartFile file) {
        List<String> productNames = clovaOCRReceiptService.extractProductNames(file);
        return ApiResponse.onSuccess(SuccessStatus.OCR_OK, productNames);
    }
}
