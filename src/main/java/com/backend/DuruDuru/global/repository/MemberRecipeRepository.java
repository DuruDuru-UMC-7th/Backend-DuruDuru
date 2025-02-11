package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.MemberRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRecipeRepository extends JpaRepository<MemberRecipe, Long> {
    List<MemberRecipe> findAllByMember(Member member);
    boolean existsByMemberAndRecipeSeq(Member member, String recipeSeq);
    void deleteByMemberAndRecipeSeq(Member member, String recipeSeq);
}