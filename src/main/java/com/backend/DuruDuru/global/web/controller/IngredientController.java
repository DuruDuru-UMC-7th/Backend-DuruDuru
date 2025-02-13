package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.converter.IngredientConverter;
import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import com.backend.DuruDuru.global.security.handler.annotation.AuthUser;
import com.backend.DuruDuru.global.service.IngredientService.IngredientCommandService;
import com.backend.DuruDuru.global.service.IngredientService.IngredientQueryService;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.InvalidRelationIdException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/ingredient")
@Tag(name = "식재료 API", description = "식재료 관련 API입니다.")
public class IngredientController {
    private final IngredientCommandService ingredientCommandService;
    private final IngredientQueryService ingredientQueryService;


    // 추가할 식재료 사진 등록
    @PostMapping(path = "/{ingredient_id}/photo", consumes = "multipart/form-data")
    @Operation(summary = "추가할 식재료 사진 등록 API", description = "추가할 식재료의 사진을 등록하는 API 입니다.")
    public ApiResponse<IngredientResponseDTO.IngredientImageDTO> updateIngredientPhoto(@ModelAttribute IngredientRequestDTO.IngredientImageRequestDTO request,
                                                                                       @Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                                       @PathVariable("ingredient_id") Long ingredientId) {
                Ingredient ingredient = ingredientCommandService.registerIngredientImage(member, ingredientId, request);
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, IngredientConverter.toIngredientImageDTO(ingredient));
    }

    // 식재료 카테고리 설정
    @PostMapping("/{ingredient_id}/category")
    @Operation(summary = "식재료 카테고리 설정 API", description = "식재료의 대분류와 소분류를 설정하는 API 입니다.")
    public ApiResponse<?> setIngredientCategory(@Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                @PathVariable("ingredient_id") Long ingredientId,
                                                @RequestBody @Valid IngredientRequestDTO.SetCategoryRequestDTO request) {
        log.info("SetCategoryRequestDTO: {}", request);
        Ingredient ingredient = ingredientCommandService.setCategory(member, ingredientId, request);
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, IngredientConverter.toSetCategoryResultDTO(ingredient));
    }

    // 식재료 보관 방식 설정
    @PostMapping("/{ingredient_id}/storage-type")
    @Operation(summary = "식재료 보관 방식 설정 API", description = "식재료의 보관 방식을 설정하는 API 입니다.")
    public ApiResponse<IngredientResponseDTO.StorageTypeResultDTO> ingredientStorageType(@Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                                         @PathVariable("ingredient_id") Long ingredientId,
                                                                                         @RequestBody IngredientRequestDTO.StorageTypeRequestDTO request) {
        Ingredient ingredient = ingredientCommandService.setStorageType(member, ingredientId, request);
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, IngredientConverter.toStorageTypeResultDTO(ingredient));
    }

    // 식재료 구매 날짜 등록
    @PostMapping("/{ingredient_id}/purchase-date")
    @Operation(summary = "식재료 구매 날짜 등록 API", description = "식재료의 구매 날짜를 등록하는 API 입니다.")
    public ApiResponse<IngredientResponseDTO.PurchaseDateResultDTO> registerPurchaseDate(@Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                                         @PathVariable("ingredient_id") Long ingredientId,
                                                                                         @RequestBody IngredientRequestDTO.PurchaseDateRequestDTO request){
        Ingredient ingredient = ingredientCommandService.registerPurchaseDate(member, ingredientId, request);
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, IngredientConverter.toPurchaseDateResultDTO(ingredient));
    }

    // 식재료 소비기한 등록
    @PostMapping("/{ingredient_id}/expiry-date")
    @Operation(summary = "식재료 소비기한 등록 API", description = "식재료의 소비기한을 등록하는 API 입니다.")
    public ApiResponse<IngredientResponseDTO.ExpiryDateResultDTO> registerExpiryDate(@Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                                     @PathVariable("ingredient_id") Long ingredientId,
                                                                                     @RequestBody IngredientRequestDTO.ExpiryDateRequestDTO request){
        Ingredient ingredient = ingredientCommandService.registerExpiryDate(member, ingredientId, request);
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, IngredientConverter.toExpiryDateResultDTO(ingredient));
    }

    // 식재료 직접 등록
    @PostMapping("/")
    @Operation(summary = "식재료 직접 등록 API", description = "식재료를 OCR 자동 인식 없이 직접 등록하는 API 입니다.")
    public ApiResponse<IngredientResponseDTO.CreateRawIngredientResultDTO> ingredientRawAdd(@Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                                            @RequestBody IngredientRequestDTO.CreateRawIngredientDTO request) {
        Ingredient newIngredient = ingredientCommandService.createRawIngredient(member, request);
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, IngredientConverter.toCreateResultDTO(newIngredient));
    }

    // 식재료 정보 수정
    @PatchMapping("/{ingredient_id}")
    @Operation(summary = "식재료 정보 수정 API", description = "식재료의 정보를 수정하는 API 입니다.")
    public ApiResponse<IngredientResponseDTO.UpdateIngredientResultDTO> updateIngredient(@Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                                         @PathVariable("ingredient_id") Long ingredientId,
                                                                                         @RequestBody IngredientRequestDTO.UpdateIngredientDTO request){
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK,
                IngredientConverter.UpdateIngredientResultDTO(ingredientCommandService.updateIngredient(member, ingredientId, request)));
    }

    // 식재료 삭제
    @DeleteMapping("/{ingredient_id}")
    @Operation(summary = "식재료 삭제 API", description = "식재료를 삭제하는 API 입니다.")
    public ApiResponse<?> deleteIngredient(@Parameter(name = "user", hidden = true) @AuthUser Member member, @PathVariable("ingredient_id") Long ingredientId){
        ingredientCommandService.deleteIngredient(member, ingredientId);
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
    }

    // 식재료 이름으로 검색
    @GetMapping("/search/name")
    @Operation(summary = "식재료 이름으로 검색 API", description = "식재료를 이름으로 검색하는 API 입니다. 입력된 키워드가 포함된 식재료를 모두 반환하며, 검색어가 없을 경우 전체 식재료 등록순으로 반환합니다.")
    public ApiResponse<IngredientResponseDTO.IngredientDetailListDTO> searchIngredientByName(@Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                                             @RequestParam Optional<String> search){
        List<Ingredient> ingredients = ingredientQueryService.getIngredientsByName(member, Optional.of(search.orElse("")));
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, IngredientConverter.toIngredientDetailListDTO(ingredients));
    }

    // 대분류에 속하는 소분류 카테고리 조회
    @GetMapping("/category/major-to-minor")
    @Operation(summary = "대분류에 속하는 소분류 카테고리 조회 API", description = "대분류에 속하는 소분류 카테고리를 조회하는 API 입니다.")
    public ApiResponse<?> majorToMinorCategory(@RequestParam MajorCategory majorCategory) {
        Map<String, Object> categoryMap = ingredientQueryService.getMinorCategoriesByMajor(majorCategory);

        List<String> minorCategoryNames = (List<String>) categoryMap.get("minorCategoryList");
        List<MinorCategory> minorCategories = minorCategoryNames.stream()
                .map(MinorCategory::valueOf)
                .collect(Collectors.toList());
        List<IngredientResponseDTO.MinorCategoryDTO> minorCategoryWithImages =
                IngredientResponseDTO.MinorCategoryDTO.fromMinorCategories(minorCategories);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("majorCategory", categoryMap.get("majorCategory"));
        response.put("minorCategoryList", minorCategoryWithImages);
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, response);
    }

    // 소분류 카테고리 목록 전체 조회
    @GetMapping("/minorCategory")
    @Operation(summary = "소분류 카테고리 목록 전체 조회 API", description = "소분류 카테고리 목록 전체를 조회하는 API 입니다.")
    public ApiResponse<List<IngredientResponseDTO.MinorCategoryDTO>> minorCategoryList(){
        List<MinorCategory> categories = Arrays.asList(MinorCategory.values());
        List<IngredientResponseDTO.MinorCategoryDTO> response = IngredientResponseDTO.MinorCategoryDTO.fromMinorCategories(categories);
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, response);
    }

    // 소분류 카테고리에 속하는 식재료 리스트 조회
    @GetMapping("/minorCategory/list")
    @Operation(summary = "소분류 카테고리에 속하는 식재료 리스트 조회 API", description = "소분류 카테고리에 속하는 식재료 리스트 조회하는 API 입니다.")
    public ApiResponse<IngredientResponseDTO.MinorCategoryIngredientPreviewListDTO> searchIngredientByMinorCategory(@Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                                                                    @RequestParam MinorCategory minorCategory) {
        List<Ingredient> ingredients = ingredientQueryService.getIngredientsByMinorCategory(member, minorCategory);
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, IngredientConverter.toMinorCategoryIngredientPreviewListDTO(minorCategory, ingredients));
    }

    // 대분류 카테고리에 속하는 식재료 리스트 조회
    @GetMapping("/majorCategory/list")
    @Operation(summary = "대분류 카테고리에 속하는 식재료 리스트 조회 API", description = "대분류 카테고리에 속하는 식재료 리스트 조회하는 API 입니다.")
    public ApiResponse<IngredientResponseDTO.MajorCategoryIngredientPreviewListDTO> searchIngredientByMajorCategory(@Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                                                                    @RequestParam MajorCategory majorCategory){
        List<Ingredient> ingredients = ingredientQueryService.getIngredientsByMajorCategory(member, majorCategory);
        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, IngredientConverter.toMajorCategoryIngredientPreviewListDTO(majorCategory, ingredients));
    }


//    // 소분류 카테고리에 속하는 식재료 개수 조회
//    @GetMapping("/category/count")
//    @Operation(summary = "식재료 카테고리 개수 조회 API", description = "식재료 카테고리의 개수를 조회하는 API 입니다. 소분류 카테고리별 식재료 개수를 반환합니다.")
//    public ApiResponse<?> countIngredientByCategory(){
//        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
//    }


//    // 새로운 식재료 카테고리 추가
//    @PostMapping("/category/add")
//    @Operation(summary = "새로운 식재료 카테고리 추가 API", description = "새로운 식재료 카테고리를 추가하는 API 입니다. 등록되지 않은 카테고리를 추가할때 사용됩니다. 사용자는 사용하지 않습니다.")
//    public ApiResponse<?> addNewCategory(){
//        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
//    }

    //    // 영수증 이미지 업로드 (식재료 목록 자동 인식)
//    @PostMapping("/receipt/upload")
//    @Operation(summary = "영수증 이미지 업로드 (식재료 목록 자동 인식) API", description = "영수증 이미지 업로드 (식재료 목록 자동 인식) API")
//    public ApiResponse<?> receiptUpload(){
//        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
//    }

//    // 식재료 카테고리 분류 (식재료 목록 자동 인식)
//    @PostMapping("/receipt/classify")
//    @Operation(summary = "식재료 카테고리 분류 (식재료 목록 자동 인식) API", description = "OCR을 통해 인식된 식재료들의 카테고리를 분류하는 API 입니다.")
//    public ApiResponse<?> receiptClassify(){
//        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
//    }


//    // 구매한 식재료 목록 조회 (식재료 목록 자동 인식)
//    @GetMapping("/purchased")
//    @Operation(summary = "구매한 식재료 목록 조회 (식재료 목록 자동 인식) API", description = "OCR을 통해 자동 등록된 식재료들의 목록을 조회하는 API 입니다.")
//    public ApiResponse<?> purchasedIngredientsList(){
//        return ApiResponse.onSuccess(SuccessStatus.INGREDIENT_OK, null);
//    }

}