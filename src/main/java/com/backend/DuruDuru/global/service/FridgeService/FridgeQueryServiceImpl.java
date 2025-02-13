package com.backend.DuruDuru.global.service.FridgeService;

import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.AuthException;
import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.repository.FridgeRepository;
import com.backend.DuruDuru.global.repository.IngredientRepository;
import com.backend.DuruDuru.global.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Getter
public class FridgeQueryServiceImpl implements FridgeQueryService {

    private final FridgeRepository fridgeRepository;
    private final IngredientRepository ingredientRepository;
    private final MemberRepository memberRepository;

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found. ID: " + memberId));
    }

    @Override
    public List<Ingredient> getAllIngredients(Member member) {
        validateLoggedInMember(member);
        findMemberById(member.getMemberId());
        List<Ingredient> ingredients = ingredientRepository.findAllByFridge_MemberOrderByCreatedAtDesc(member);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getIngredientsNearExpiry(Long memberId) {
        findMemberById(memberId);
        List<Ingredient> ingredients = ingredientRepository.findAllByFridge_Member_MemberIdOrderByDDayAsc(memberId);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getIngredientsFarExpiry(Long memberId) {
        findMemberById(memberId);
        List<Ingredient> ingredients = ingredientRepository.findAllByFridge_Member_MemberIdOrderByDDayDesc(memberId);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getAllMajorCategoryIngredients(Long memberId, MajorCategory majorCategory) {
        List<Ingredient> ingredients = ingredientRepository.findAllByFridge_Member_MemberIdAndMajorCategoryOrderByCreatedAtDesc(memberId, majorCategory);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getMajorCategoryIngredientsNearExpiry(Long memberId, MajorCategory majorCategory) {
        List<Ingredient> ingredients = ingredientRepository.findAllByFridge_Member_MemberIdAndMajorCategoryOrderByDDayAsc(memberId, majorCategory);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getMajorCategoryIngredientsFarExpiry(Long memberId, MajorCategory majorCategory) {
        List<Ingredient> ingredients = ingredientRepository.findAllByFridge_Member_MemberIdAndMajorCategoryOrderByDDayDesc(memberId, majorCategory);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getIngredientsByNameRecent(Long memberId, Optional<String> optSearch) {
        findMemberById(memberId);
        List<Ingredient> ingredients;
        if (optSearch.isPresent() && !optSearch.get().trim().isEmpty()) {
            String search = optSearch.get();
            ingredients = ingredientRepository.findAllByFridge_Member_MemberIdAndIngredientNameContainingIgnoreCaseOrderByCreatedAtDesc(memberId, search);
        } else {
            ingredients = ingredientRepository.findAllByFridge_MemberOrderByCreatedAtDesc(findMemberById(memberId));
        }
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getIngredientsByNameNearExpiry(Long memberId, Optional<String> optSearch) {
        findMemberById(memberId);
        List<Ingredient> ingredients;
        if (optSearch.isPresent() && !optSearch.get().trim().isEmpty()) {
            String search = optSearch.get();
            ingredients = ingredientRepository.findAllByFridge_Member_MemberIdAndIngredientNameContainingIgnoreCaseOrderByDDayAsc(memberId, search);
        } else {
            ingredients = ingredientRepository.findAllByFridge_Member_MemberIdOrderByDDayAsc(memberId);
        }
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getIngredientsByNameFarExpiry(Long memberId, Optional<String> optSearch) {
        findMemberById(memberId);
        List<Ingredient> ingredients;
        if (optSearch.isPresent() && !optSearch.get().trim().isEmpty()) {
            String search = optSearch.get();
            ingredients = ingredientRepository.findAllByFridge_Member_MemberIdAndIngredientNameContainingIgnoreCaseOrderByDDayDesc(memberId, search);
        } else {
            ingredients = ingredientRepository.findAllByFridge_Member_MemberIdOrderByDDayDesc(memberId);
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
