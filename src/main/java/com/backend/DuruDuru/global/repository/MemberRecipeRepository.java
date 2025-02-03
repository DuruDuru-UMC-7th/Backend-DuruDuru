package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.MemberRecipe;
import com.backend.DuruDuru.global.domain.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRecipeRepository extends JpaRepository<MemberRecipe, Long> {
    MemberRecipe findByMemberAndRecipe(Member member, Recipe recipe);
}