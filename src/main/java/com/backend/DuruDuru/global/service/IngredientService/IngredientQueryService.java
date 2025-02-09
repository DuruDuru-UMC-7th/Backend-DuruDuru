package com.backend.DuruDuru.global.service.IngredientService;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IngredientQueryService {
    Map<String, Object> getMinorCategoriesByMajor(MajorCategory majorCategory);
    List<Ingredient> getIngredientsByMinorCategory(Long memberId, MinorCategory minorCategory);
    List<Ingredient> getIngredientsByMajorCategory(Long memberId, MajorCategory majorCategory);
    List<Ingredient> getIngredientsByName(Optional<String> optSearch);
}
