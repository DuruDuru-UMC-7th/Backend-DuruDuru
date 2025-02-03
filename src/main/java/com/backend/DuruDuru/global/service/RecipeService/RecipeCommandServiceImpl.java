package com.backend.DuruDuru.global.service.RecipeService;

import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.RecipeException;
import com.backend.DuruDuru.global.converter.RecipeConverter;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.MemberRecipe;
import com.backend.DuruDuru.global.domain.entity.Recipe;
import com.backend.DuruDuru.global.repository.MemberRecipeRepository;
import com.backend.DuruDuru.global.repository.MemberRepository;
import com.backend.DuruDuru.global.repository.RecipeRepository;
import com.backend.DuruDuru.global.web.dto.Recipe.RecipeResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.amazonaws.services.ec2.model.LaunchTemplateHttpTokensState.Optional;

@Service
@RequiredArgsConstructor
public class RecipeCommandServiceImpl implements RecipeCommandService {

    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final MemberRecipeRepository memberRecipeRepository;

    @Override
    @Transactional
    public RecipeResponseDTO.DetailResponse getRecipeById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeException(ErrorStatus.RECIPE_NOT_FOUND));

        return RecipeConverter.toDetailResponse(recipe);
    }


    @Override
    @Transactional
    public void setRecipeFavorite(Member member, Long recipeId) {
        // MemberRecipe 조회
        Recipe addRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeException(ErrorStatus.RECIPE_NOT_FOUND));
        Member addMember = memberRepository.findById(member.getMemberId())
                .orElseThrow(() -> new RecipeException(ErrorStatus.MEMBER_NOT_FOUND));

        MemberRecipe favorite = memberRecipeRepository.findByMemberAndRecipe(addMember, addRecipe);

        if (favorite != null) {
            // 이미 즐겨찾기 된 경우 삭제
            memberRecipeRepository.delete(favorite);
        } else {
            // 즐겨찾기 추가
            MemberRecipe newFavorite = MemberRecipe.builder()
                    .member(addMember)
                    .recipe(addRecipe)
                    .build();

            memberRecipeRepository.save(newFavorite);
        }
    }

    @Override
    public List<RecipeResponseDTO.DetailResponse> getFavoriteRecipes(Member member) {
        // MemberRecipe에서 특정 회원의 즐겨찾기 목록 조회
        List<MemberRecipe> favorites = memberRecipeRepository.findAllByMember(member);

        return favorites.stream()
                .map(favorite -> RecipeConverter.toDetailResponse(favorite.getRecipe()))
                .collect(Collectors.toList());
    }
}
