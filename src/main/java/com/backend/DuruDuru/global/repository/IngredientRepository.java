package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByMember_MemberIdAndMinorCategory(Long memberId, MinorCategory minorCategory);
    List<Ingredient> findAllByIngredientNameContainingIgnoreCaseOrderByCreatedAtDesc(String search);
    List<Ingredient> findAllByOrderByCreatedAtDesc();

    // 냉장고 관련 메서드
    List<Ingredient> findAllByFridge_Member_MemberIdOrderByCreatedAtDesc(Long memberId);
    List<Ingredient> findAllByFridge_Member_MemberIdOrderByExpiryDateAsc(Long memberId);

}
