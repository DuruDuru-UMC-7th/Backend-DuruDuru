package com.backend.DuruDuru.global.service.IngredientService;

import java.util.*;
import java.util.stream.Collectors;

import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IngredientQueryServiceImpl implements IngredientQueryService {

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

}
