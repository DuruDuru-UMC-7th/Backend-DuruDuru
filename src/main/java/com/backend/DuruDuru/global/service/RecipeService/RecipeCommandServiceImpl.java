package com.backend.DuruDuru.global.service.RecipeService;

import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.RecipeException;
import com.backend.DuruDuru.global.converter.RecipeConverter;
import com.backend.DuruDuru.global.domain.entity.Recipe;
import com.backend.DuruDuru.global.repository.RecipeRepository;
import com.backend.DuruDuru.global.web.dto.Recipe.RecipeResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;

@Service
@RequiredArgsConstructor
public class RecipeCommandServiceImpl implements RecipeCommandService {

    private final RecipeRepository recipeRepository;

    @Override
    @Transactional
    public RecipeResponseDTO.DetailResponse getRecipeById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeException(ErrorStatus.RECIPE_NOT_FOUND));

        return RecipeConverter.toDetailResponse(recipe);
    }
}
