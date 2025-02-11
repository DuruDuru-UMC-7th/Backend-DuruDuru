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

    // 특정 레시피 조회
    @Override
    @Transactional
    public RecipeResponseDTO.RecipeDetailResponse getRecipeDetailById(String recipeId) {
        String url = buildApiUrl(recipeId);

        RecipeResponseDTO.RecipeApiResponse apiResponse = restTemplate.getForObject(url, RecipeResponseDTO.RecipeApiResponse.class);

        if (apiResponse == null || apiResponse.getRecipes() == null || apiResponse.getRecipes().isEmpty()) {
            throw new RecipeException(ErrorStatus.RECIPE_NOT_FOUND);
        }

        RecipeResponseDTO.RecipeApiResponse.Recipe recipe = apiResponse.getRecipes().get(0);

        // 메뉴명 제거 및 재료 정보 콤마로 구분
        String cleanedIngredients = cleanIngredients(recipe.getRcpNm(), recipe.getRcpPartsDtls());

        // 만드는 법 단계에서 마지막 알파벳 제거
        List<String> cleanedManualSteps = cleanManualSteps(recipe.getManualSteps());

        return RecipeResponseDTO.RecipeDetailResponse.builder()
                .recipeId(recipe.getRcpSeq())
                .recipeName(recipe.getRcpNm())
                .cookingMethod(recipe.getRcpWay2())
                .recipeType(recipe.getRcpPat2())
                .ingredients(cleanedIngredients)
                .imageUrl(recipe.getAttFileNoMk())
                .manualSteps(cleanedManualSteps)
                .build();
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
                .totalPages(totalPages)
                .totalElements(totalElements)
                .recipes(recipes)
                .build();
    }

    private long getTotalElements(String ingredients) {
        int batchSize = 200;
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

    // 기본값
    private String buildApiUrl(String recipeId) {
        return UriComponentsBuilder.fromHttpUrl("http://openapi.foodsafetykorea.go.kr/api")
                .pathSegment(keyId, "COOKRCP01", "json", "1", "1")
                .queryParam("RCP_SEQ", recipeId)
                .toUriString();
    }

    // 페이징용
    private String buildApiUrl(String ingredients, int startIdx, int endIdx) {
        return UriComponentsBuilder.fromHttpUrl("http://openapi.foodsafetykorea.go.kr/api")
                .pathSegment(keyId, "COOKRCP01", "json", String.valueOf(startIdx), String.valueOf(endIdx))
                .queryParam("RCP_PARTS_DTLS", ingredients)
                .toUriString();
    }

    private String cleanIngredients(String recipeName, String ingredients) {
        // 메뉴명에서 공백을 제거한 형태로 비교
        String normalizedRecipeName = recipeName.replace(" ", "");

        // 메뉴명 제거 및 여분의 첫 번째 콤마 제거
        String cleanedIngredients = ingredients.replace(normalizedRecipeName, "").trim();

        // 메뉴명 뒤에 있는 첫 번째 콤마 제거
        if (cleanedIngredients.startsWith(",")) {
            cleanedIngredients = cleanedIngredients.substring(1).trim();
        }

        // 개행으로 감싸진 불필요한 섹션 제거 후 줄바꿈을 콤마로 변환
        cleanedIngredients = cleanedIngredients.replaceAll("\n[^,\n]+\n", "\n")
                .replace("\n", ", ")
                .trim();

        return cleanedIngredients;
    }


    private List<String> cleanManualSteps(List<String> manualSteps) {
        return manualSteps.stream()
                .map(step -> step.replaceAll("[a-zA-Z]$", "").trim())  // 마지막 알파벳 제거
                .collect(Collectors.toList());
    }
}
