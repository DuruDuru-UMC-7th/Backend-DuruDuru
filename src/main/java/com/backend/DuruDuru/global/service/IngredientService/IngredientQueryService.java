package com.backend.DuruDuru.global.service.IngredientService;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IngredientQueryService {
    Map<String, Object> getMinorCategoriesByMajor(MajorCategory majorCategory);
    List<Ingredient> getIngredientsByMinorCategory(Member member, MinorCategory minorCategory);
    List<Ingredient> getIngredientsByMajorCategory(Member member, MajorCategory majorCategory);
    List<Ingredient> getIngredientsByName(Member member, Optional<String> optSearch);
}
