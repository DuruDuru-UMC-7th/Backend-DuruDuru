package com.backend.DuruDuru.global.service.IngredientService;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IngredientQueryService {
    // 대분류에 속하는 소분류 카테고리 조회
    Map<String, Object> getMinorCategoriesByMajor(MajorCategory majorCategory);
    // 소분류에 속하는 등록된 식재료 리스트 조회
    List<Ingredient> getIngredientsByMinorCategory(Member member, MinorCategory minorCategory);
    // 대분류에 속하는 등록된 식재료 리스트 조회
    List<Ingredient> getIngredientsByMajorCategory(Member member, MajorCategory majorCategory);
    // 식재료 이름으로 검색
    List<Ingredient> getIngredientsByName(Member member, Optional<String> optSearch);
}
