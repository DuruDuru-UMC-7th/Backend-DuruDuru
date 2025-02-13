package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByMember_MemberIdAndMinorCategory(Long memberId, MinorCategory minorCategory);
    List<Ingredient> findByMember_MemberIdAndMajorCategory(Long memberId, MajorCategory majorCategory);
    // 사용자가 등록한 식재료 중 검색어가 포함된 식재료 조회 (대소문자 무시)
    List<Ingredient> findAllByMember_MemberIdAndIngredientNameContainingIgnoreCaseOrderByCreatedAtDesc(Long memberId, String search);
    // 사용자가 등록한 모든 식재료 최신순 조회
    List<Ingredient> findAllByMember_MemberIdOrderByCreatedAtDesc(Long memberId);


    // -- 냉장고 관련 -- //
    // 사용자 식재료 최신순 조회
    List<Ingredient> findAllByFridge_MemberOrderByCreatedAtDesc(Member member);
    // 사용자 식재료 소비기한 임박순 조회
    @Query("SELECT i FROM Ingredient i WHERE i.fridge.member = :member ORDER BY i.dDay ASC")
    List<Ingredient> findAllByFridge_MemberOrderByDDayAsc(@Param("member") Member member);
    // 사용자 식재료 소비기한 여유순 조회
    @Query("SELECT i FROM Ingredient i WHERE i.fridge.member = :member ORDER BY i.dDay DESC")
    List<Ingredient> findAllByFridge_MemberOrderByDDayDesc(@Param("member") Member member);

    // 사용자 식재료 대분류 기준 최신순 조회
    List<Ingredient> findAllByFridge_MemberAndMajorCategoryOrderByCreatedAtDesc(Member member, MajorCategory majorCategory);
    // 사용자 식재료 대분류 기준 소비기한 임박순 조회
    @Query("SELECT i FROM Ingredient i WHERE i.fridge.member = :member AND i.majorCategory = :majorCategory ORDER BY i.dDay ASC")
    List<Ingredient> findAllByFridge_MemberAndMajorCategoryOrderByDDayAsc(Member member, MajorCategory majorCategory);
    // 사용자 식재료 대분류 기준 소비기한 여유순 조회
    @Query("SELECT i FROM Ingredient i WHERE i.fridge.member = :member AND i.majorCategory = :majorCategory ORDER BY i.dDay DESC")
    List<Ingredient> findAllByFridge_MemberAndMajorCategoryOrderByDDayDesc(Member member, MajorCategory majorCategory);

    // 사용자가 식재료 검색어가 포함된 식재료 조회 (대소문자 무시)
    List<Ingredient> findAllByFridge_MemberAndIngredientNameContainingIgnoreCaseOrderByCreatedAtDesc(Member member, String search);
    // 사용자가 식재료 검색어가 포함된 식재료 소비기한 임박순 조회
    @Query("SELECT i FROM Ingredient i WHERE i.fridge.member = :member AND i.ingredientName LIKE %:search% ORDER BY i.dDay ASC")
    List<Ingredient> findAllByFridge_MemberAndIngredientNameContainingIgnoreCaseOrderByDDayAsc(Member member, String search);
    // 사용자가 식재료 검색어가 포함된 식재료 소비기한 여유순 조회
    @Query("SELECT i FROM Ingredient i WHERE i.fridge.member = :member AND i.ingredientName LIKE %:search% ORDER BY i.dDay DESC")
    List<Ingredient> findAllByFridge_MemberAndIngredientNameContainingIgnoreCaseOrderByDDayDesc(Member member, String search);


}
