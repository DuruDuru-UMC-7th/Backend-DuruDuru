package com.backend.DuruDuru.global.service.FridgeService;

import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.AuthException;
import com.backend.DuruDuru.global.apiPayload.exception.handler.FridgeHandler;
import com.backend.DuruDuru.global.apiPayload.exception.handler.IngredientHandler;
import com.backend.DuruDuru.global.apiPayload.exception.handler.MemberHandler;
import com.backend.DuruDuru.global.domain.entity.Fridge;
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
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_ID_NULL));
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
    public List<Ingredient> getIngredientsNearExpiry(Member member) {
        validateLoggedInMember(member);
        findMemberById(member.getMemberId());
        List<Ingredient> ingredients = ingredientRepository.findAllByFridge_MemberOrderByDDayAsc(member);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getIngredientsFarExpiry(Member member) {
        validateLoggedInMember(member);
        findMemberById(member.getMemberId());
        List<Ingredient> ingredients = ingredientRepository.findAllByFridge_MemberOrderByDDayDesc(member);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getAllMajorCategoryIngredients(Member member, MajorCategory majorCategory) {
        validateLoggedInMember(member);
        List<Ingredient> ingredients = ingredientRepository.findAllByFridge_MemberAndMajorCategoryOrderByCreatedAtDesc(member, majorCategory);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getMajorCategoryIngredientsNearExpiry(Member member, MajorCategory majorCategory) {
        validateLoggedInMember(member);
        List<Ingredient> ingredients = ingredientRepository.findAllByFridge_MemberAndMajorCategoryOrderByDDayAsc(member, majorCategory);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getMajorCategoryIngredientsFarExpiry(Member member, MajorCategory majorCategory) {
        validateLoggedInMember(member);
        List<Ingredient> ingredients = ingredientRepository.findAllByFridge_MemberAndMajorCategoryOrderByDDayDesc(member, majorCategory);
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getIngredientsByNameRecent(Member member, Optional<String> optSearch) {
        validateLoggedInMember(member);
        findMemberById(member.getMemberId());
        List<Ingredient> ingredients;
        if (optSearch.isPresent() && !optSearch.get().trim().isEmpty()) {
            String search = optSearch.get();
            ingredients = ingredientRepository.findAllByFridge_MemberAndIngredientNameContainingIgnoreCaseOrderByCreatedAtDesc(member, search);
        } else {
            ingredients = ingredientRepository.findAllByFridge_MemberOrderByCreatedAtDesc(member);
        }
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getIngredientsByNameNearExpiry(Member member, Optional<String> optSearch) {
        validateLoggedInMember(member);
        findMemberById(member.getMemberId());
        List<Ingredient> ingredients;
        if (optSearch.isPresent() && !optSearch.get().trim().isEmpty()) {
            String search = optSearch.get();
            ingredients = ingredientRepository.findAllByFridge_MemberAndIngredientNameContainingIgnoreCaseOrderByDDayAsc(member, search);
        } else {
            ingredients = ingredientRepository.findAllByFridge_MemberOrderByDDayAsc(member);
        }
        validateIngredientProperties(ingredients);
        return ingredients;
    }

    @Override
    public List<Ingredient> getIngredientsByNameFarExpiry(Member member, Optional<String> optSearch) {
        validateLoggedInMember(member);
        findMemberById(member.getMemberId());
        List<Ingredient> ingredients;
        if (optSearch.isPresent() && !optSearch.get().trim().isEmpty()) {
            String search = optSearch.get();
            ingredients = ingredientRepository.findAllByFridge_MemberAndIngredientNameContainingIgnoreCaseOrderByDDayDesc(member, search);
        } else {
            ingredients = ingredientRepository.findAllByFridge_MemberOrderByDDayDesc(member);
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
