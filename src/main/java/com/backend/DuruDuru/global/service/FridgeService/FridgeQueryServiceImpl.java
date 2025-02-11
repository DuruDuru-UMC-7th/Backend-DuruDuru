package com.backend.DuruDuru.global.service.FridgeService;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.repository.FridgeRepository;
import com.backend.DuruDuru.global.repository.IngredientRepository;
import com.backend.DuruDuru.global.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Ingredient> getAllIngredients(Long memberId) {
        findMemberById(memberId);
        List<Ingredient> ingredients = ingredientRepository.findAllByFridge_Member_MemberIdOrderByCreatedAtDesc(memberId);
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
