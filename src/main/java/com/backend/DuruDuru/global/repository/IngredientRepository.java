package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByMember_MemberIdAndMinorCategory(Long memberId, MinorCategory minorCategory);
    List<Ingredient> findAllByIngredientNameContainingIgnoreCaseOrderByCreatedAtDesc(String search);
    List<Ingredient> findAllByOrderByCreatedAtDesc();

    // 냉장고 관련 메서드
    List<Ingredient> findAllByFridge_Member_MemberIdOrderByCreatedAtDesc(Long memberId);
//    List<Ingredient> findAllByFridge_Member_MemberIdOrderByDDayAsc(Long memberId);
//    List<Ingredient> findAllByFridge_Member_MemberIdOrderByDDayDesc(Long memberId);
    @Query("SELECT i FROM Ingredient i WHERE i.fridge.member.memberId = :memberId ORDER BY i.dDay ASC")
    List<Ingredient> findAllByFridge_Member_MemberIdOrderByDDayAsc(@Param("memberId") Long memberId);

    @Query("SELECT i FROM Ingredient i WHERE i.fridge.member.memberId = :memberId ORDER BY i.dDay DESC")
    List<Ingredient> findAllByFridge_Member_MemberIdOrderByDDayDesc(@Param("memberId") Long memberId);

}
