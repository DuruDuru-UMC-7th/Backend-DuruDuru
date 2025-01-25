package com.backend.DuruDuru.global.service.IngredientService;

import com.backend.DuruDuru.global.domain.enums.MajorCategory;

import java.util.Map;

public interface IngredientQueryService {
    // 대분류에 따른 소분류 가져오기
    Map<String, Object> getMinorCategoriesByMajor(MajorCategory majorCategory);

}
