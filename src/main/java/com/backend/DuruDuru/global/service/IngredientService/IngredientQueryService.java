package com.backend.DuruDuru.global.service.IngredientService;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IngredientQueryService {
    // 대분류에 따른 소분류 가져오기
    Map<String, Object> getMinorCategoriesByMajor(MajorCategory majorCategory);
    List<Ingredient> getIngredientsByMinorCategory(Long memberId, MinorCategory minorCategory);
    List<Ingredient> getIngredientsByName(Optional<String> optSearch);
}
