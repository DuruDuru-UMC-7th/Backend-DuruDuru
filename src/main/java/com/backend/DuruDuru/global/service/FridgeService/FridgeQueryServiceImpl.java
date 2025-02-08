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
        return ingredientRepository.findAllByFridge_Member_MemberIdOrderByCreatedAtDesc(memberId);
    }

    @Override
    public List<Ingredient> getIngredientsNearExpiry(Long memberId) {
        findMemberById(memberId);
        return ingredientRepository.findAllByFridge_Member_MemberIdOrderByDDayAsc(memberId);
    }

    @Override
    public List<Ingredient> getIngredientsFarExpiry(Long memberId) {
        findMemberById(memberId);
        return ingredientRepository.findAllByFridge_Member_MemberIdOrderByDDayDesc(memberId);
    }

}
