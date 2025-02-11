package com.backend.DuruDuru.global.service.IngredientService;

import java.util.*;
import java.util.stream.Collectors;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import com.backend.DuruDuru.global.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IngredientQueryServiceImpl implements IngredientQueryService {
    private final IngredientRepository ingredientRepository;

    @Override
    public Map<String, Object> getMinorCategoriesByMajor(MajorCategory majorCategory) {
        // 대분류에 속하는 소분류 리스트 필터링
        List<String> minorCategoryList = Arrays.stream(MinorCategory.values())
                .filter(minor -> minor.getMajorCategory() == majorCategory)
                .map(Enum::name)
                .collect(Collectors.toList());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("majorCategory", majorCategory.name());
        result.put("minorCategoryList", minorCategoryList);

        return result;
    }

    // 소분류 카테고리에 속하는 식재료 조회
    @Override
    public List<Ingredient> getIngredientsByMinorCategory(Long memberId, MinorCategory minorCategory) {
        List<Ingredient> ingredients = ingredientRepository.findByMember_MemberIdAndMinorCategory(memberId, minorCategory);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    // 대분류 카테고리에 속하는 식재료 조회
    @Override
    public List<Ingredient> getIngredientsByMajorCategory(Long memberId, MajorCategory majorCategory) {
        List<Ingredient> ingredients = ingredientRepository.findByMember_MemberIdAndMajorCategory(memberId, majorCategory);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    // 식재료 이름으로 검색
    @Override
    public List<Ingredient> getIngredientsByName(Optional<String> optSearch) {
        List<Ingredient> ingredients;
        if (optSearch.isPresent()) {
            String search = optSearch.get();
            ingredients = ingredientRepository.findAllByIngredientNameContainingIgnoreCaseOrderByCreatedAtDesc(search);
        } else {
            ingredients = ingredientRepository.findAllByOrderByCreatedAtDesc();
        }
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    // 식재료 필수 속성 미설정 예외처리 (카테고리, 보관방식, 구매날짜, 소비기한)
    private void validateIngredientProperties(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getMajorCategory() == null || ingredient.getMinorCategory() == null) {
                throw new IllegalStateException("카테고리 설정이 미완료된 식재료가 존재합니다.");
            }
            if (ingredient.getStorageType() == null) {
                throw new IllegalStateException("보관방식 설정이 미완료된 식재료가 존재합니다.");
            }
            if (ingredient.getPurchaseDate() == null) {
                throw new IllegalStateException("구매날짜 설정이 미완료된 식재료가 존재합니다.");
            }
            if (ingredient.getExpiryDate() == null) {
                throw new IllegalStateException("소비기한 설정이 미완료된 식재료가 존재합니다.");
            }
        }
    }

}
