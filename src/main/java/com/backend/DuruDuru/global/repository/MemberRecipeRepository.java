package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.MemberRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRecipeRepository extends JpaRepository<MemberRecipe, Long> {
    Page<MemberRecipe> findByMember(Member member, Pageable pageable);
    boolean existsByMemberAndRecipeName(Member member, String recipeName);
    void deleteByMemberAndRecipeName(Member member, String recipeName);
    long countByRecipeName(String recipeName);

    @Query("SELECT r.recipeName, COUNT(r) AS favoriteCount FROM MemberRecipe r " +
            "GROUP BY r.recipeName " +
            "ORDER BY favoriteCount DESC")
    Page<Object[]> findPopularRecipes(Pageable pageable);
}