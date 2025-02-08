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

import java.util.List;

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
        return ingredientRepository.findAllByFridge_Member_MemberIdOrderByCreatedAtDesc(memberId);
    }

    @Override
    public List<Ingredient> getIngredientsNearExpiry(Long memberId) {
        findMemberById(memberId);
        return ingredientRepository.findAllByFridge_Member_MemberIdOrderByExpiryDateAsc(memberId);
    }

    @Override
    public List<Ingredient> getIngredientsFarExpiry(Long memberId) {
        findMemberById(memberId);
        return ingredientRepository.findAllByFridge_Member_MemberIdOrderByExpiryDateDesc(memberId);
    }

}
