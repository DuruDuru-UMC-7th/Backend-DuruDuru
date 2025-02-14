package com.backend.DuruDuru.global.service.IngredientService;

import java.util.*;
import java.util.stream.Collectors;

import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.AuthException;
import com.backend.DuruDuru.global.apiPayload.exception.handler.MemberHandler;
import com.backend.DuruDuru.global.domain.entity.Fridge;
import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import com.backend.DuruDuru.global.repository.FridgeRepository;
import com.backend.DuruDuru.global.repository.IngredientRepository;
import com.backend.DuruDuru.global.repository.MemberRepository;
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
    private final MemberRepository memberRepository;


    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_ID_NULL));
    }

    // 대분류에 속하는 소분류 카테고리 조회
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
    public List<Ingredient> getIngredientsByMinorCategory(Member member, MinorCategory minorCategory) {
        validateLoggedInMember(member);
        List<Ingredient> ingredients = ingredientRepository.findByMemberAndMinorCategory(member, minorCategory);

        validateIngredientProperties(ingredients);
        return ingredients;
    }

    // 대분류 카테고리에 속하는 식재료 조회
    @Override
    public List<Ingredient> getIngredientsByMajorCategory(Member member, MajorCategory majorCategory) {
        validateLoggedInMember(member);
        List<Ingredient> ingredients = ingredientRepository.findByMemberAndMajorCategory(member, majorCategory);

        validateIngredientProperties(ingredients);
        return ingredients;
    }

    // 식재료 이름으로 검색
    @Override
    public List<Ingredient> getIngredientsByName(Member member, Optional<String> optSearch) {
        validateLoggedInMember(member);
        findMemberById(member.getMemberId());
        List<Ingredient> ingredients;
        if (optSearch.isPresent() && !optSearch.get().trim().isEmpty()) {
            String search = optSearch.get();
            ingredients = ingredientRepository.findAllByMemberAndIngredientNameContainingIgnoreCaseOrderByCreatedAtDesc(member, search);
        } else {
            ingredients = ingredientRepository.findAllByMemberOrderByCreatedAtDesc(member);
        }

        validateIngredientProperties(ingredients);
        return ingredients;
    }


    // 로그인 여부 확인
    private void validateLoggedInMember(Member member) {
        if (member == null) {
            throw new AuthException(ErrorStatus.LOGIN_REQUIRED);
        }
    }

    // 식재료 필수 속성 미설정 예외처리 (카테고리, 보관방식, 구매날짜, 소비기한)
    private void validateIngredientProperties(List<Ingredient> ingredients) {
        List<String> errorMessages = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            String ingredientInfo = "식재료 ID: " + ingredient.getIngredientId() + " (" + ingredient.getIngredientName() + ")";

            if (ingredient.getMajorCategory() == null || ingredient.getMinorCategory() == null) {
                errorMessages.add(ingredientInfo + " → 카테고리 설정이 미완료 상태입니다.");
            }
            if (ingredient.getStorageType() == null) {
                errorMessages.add(ingredientInfo + " → 보관방식 설정이 미완료 상태입니다.");
            }
            if (ingredient.getPurchaseDate() == null) {
                errorMessages.add(ingredientInfo + " → 구매날짜 설정이 미완료 상태입니다.");
            }
            if (ingredient.getExpiryDate() == null) {
                errorMessages.add(ingredientInfo + " → 소비기한 설정이 미완료 상태입니다.");
            }
        }
        if (!errorMessages.isEmpty()) {
            throw new IllegalStateException("식재료 필수 속성이 누락되어있습니다: " + String.join(" | ", errorMessages));
        }
    }

}
