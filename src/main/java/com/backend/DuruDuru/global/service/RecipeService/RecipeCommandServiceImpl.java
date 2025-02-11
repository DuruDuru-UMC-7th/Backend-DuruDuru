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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeCommandServiceImpl implements RecipeCommandService {

    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final MemberRecipeRepository memberRecipeRepository;

    @Value("${api.foodsafety.keyId}")
    private String keyId;
    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public RecipeResponseDTO.RecipeResponse getRecipeById(Long recipeId) {
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
    public List<RecipeResponseDTO.RecipeResponse> getFavoriteRecipes(Member member) {
        // MemberRecipe에서 특정 회원의 즐겨찾기 목록 조회
        List<MemberRecipe> favorites = memberRecipeRepository.findAllByMember(member);

        return favorites.stream()
                .map(favorite -> RecipeConverter.toDetailResponse(favorite.getRecipe()))
                .collect(Collectors.toList());
    }

    @Override
    public RecipeResponseDTO.RecipePageResponse getPopularRecipes(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Recipe> recipes = recipeRepository.findAllByPopular(pageable);

        List<RecipeResponseDTO.RecipeResponse> recipeResponses = recipes.stream()
                .map(recipe -> RecipeResponseDTO.RecipeResponse.builder()
                        //.recipeId(recipe.getRecipeId())
                        //.title(recipe.getTitle())
                        //.content(recipe.getContent())
                        .build()
                )
                .toList();

        return RecipeResponseDTO.RecipePageResponse.builder()
                .page(page)
                .size(size)
                .totalPages(recipes.getTotalPages())
                .totalElements(recipes.getTotalElements())
                .recipes(recipeResponses)
                .build();
    }

    // 식재료 기반 레시피 추천
    @Override
    public RecipeResponseDTO.RecipePageResponse searchRecipes(String ingredients, int page, int size) {
        int startIdx = (page - 1) * size + 1;
        int endIdx = page * size;

        // 전체 항목 개수 계산
        long totalElements = getTotalElements(ingredients);
        int totalPages = (int) Math.ceil((double) totalElements / size);

        String url = buildApiUrl(ingredients, startIdx, endIdx);

        RecipeResponseDTO.RecipeApiResponse apiResponse = restTemplate.getForObject(url, RecipeResponseDTO.RecipeApiResponse.class);

        List<RecipeResponseDTO.RecipeResponse> recipes = apiResponse.getRecipes().stream()
                .map(recipe -> RecipeResponseDTO.RecipeResponse.builder()
                        .recipeId(recipe.getRcpSeq())
                        .imageUrl(recipe.getAttFileNoMain())
                        .build())
                .collect(Collectors.toList());

        return RecipeResponseDTO.RecipePageResponse.builder()
                .page(page)
                .size(size)
                .totalPages(totalPages)  // 임시값, API에서 제공되는 데이터에 따라 변경 필요
                .totalElements(totalElements)  // 임시값
                .recipes(recipes)
                .build();
    }

    private long getTotalElements(String ingredients) {
        int batchSize = 1000;
        int currentStartIdx = 1;
        int currentEndIdx = batchSize;
        long totalCount = 0;

        while (true) {
            String url = buildApiUrl(ingredients, currentStartIdx, currentEndIdx);
            RecipeResponseDTO.RecipeApiResponse apiResponse = restTemplate.getForObject(url, RecipeResponseDTO.RecipeApiResponse.class);

            if (apiResponse == null || apiResponse.getRecipes() == null || apiResponse.getRecipes().isEmpty()) {
                break;  // 데이터가 없으면 루프 종료
            }

            totalCount += apiResponse.getRecipes().size();
            if (apiResponse.getRecipes().size() < batchSize) {
                break;  // 마지막 페이지에 도달한 경우 루프 종료
            }

            currentStartIdx += batchSize;
            currentEndIdx += batchSize;
        }

        return totalCount;
    }
    private String buildApiUrl(String ingredients, int startIdx, int endIdx) {
        return UriComponentsBuilder.fromHttpUrl("http://openapi.foodsafetykorea.go.kr/api")
                .pathSegment(keyId, "COOKRCP01", "json", String.valueOf(startIdx), String.valueOf(endIdx))
                .queryParam("RCP_PARTS_DTLS", ingredients)
                .toUriString();
    }

}
