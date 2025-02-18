package com.backend.DuruDuru.global.service.RecipeService;

import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.RecipeException;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.MemberRecipe;
import com.backend.DuruDuru.global.repository.MemberRecipeRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeCommandServiceImpl implements RecipeCommandService {

    private final MemberRecipeRepository memberRecipeRepository;
    private final RestTemplate restTemplate;

    @Value("${api.foodsafety.keyId}")
    private String keyId;

    // 특정 레시피 조회
    @Override
    @Transactional
    public RecipeResponseDTO.RecipeDetailResponse getRecipeDetailByName(String recipeName) {
        String url = buildApiUrl(recipeName);
        RecipeResponseDTO.RecipeApiResponse apiResponse = restTemplate.getForObject(url, RecipeResponseDTO.RecipeApiResponse.class);

        if (apiResponse == null || apiResponse.getRecipes() == null || apiResponse.getRecipes().isEmpty()) {
            throw new RecipeException(ErrorStatus.RECIPE_NOT_FOUND);
        }

        RecipeResponseDTO.RecipeApiResponse.Recipe recipe = apiResponse.getRecipes().get(0);

        // 메뉴명 제거 및 재료 정보 콤마로 구분
        String cleanedIngredients = cleanIngredients(recipe.getRcpPartsDtls());
        // 만드는 법 단계에서 마지막 알파벳 제거
        List<String> cleanedManualSteps = cleanManualSteps(recipe.getManualSteps());

        long favoriteCount = memberRecipeRepository.countByRecipeName(recipeName);

        return RecipeResponseDTO.RecipeDetailResponse.builder()
                .recipeName(recipe.getRcpNm())
                .cookingMethod(recipe.getRcpWay2())
                .recipeType(recipe.getRcpPat2())
                .ingredients(cleanedIngredients)
                .imageUrl(recipe.getAttFileNoMk())
                .manualSteps(cleanedManualSteps)
                .favoriteCount(favoriteCount)
                .build();
    }


    // 레시피 즐겨찾기
    @Override
    @Transactional
    public void setRecipeFavorite(Member member, String recipeName) {
      if (memberRecipeRepository.existsByMemberAndRecipeName(member, recipeName)) {
          memberRecipeRepository.deleteByMemberAndRecipeName(member, recipeName);
      } else {
          MemberRecipe memberRecipe = MemberRecipe.builder()
                  .member(member)
                  .recipeName(recipeName)
                  .build();
          memberRecipeRepository.save(memberRecipe);
      }
    }


    // 식재료 기반 레시피 추천
    @Override
    @Transactional
    public RecipeResponseDTO.RecipePageResponse searchRecipes(String ingredients, int page, int size) {
        int startIdx = (page - 1) * size + 1;
        int endIdx = page * size;

        // 전체 항목 개수 계산
        long totalElements = getTotalElements(ingredients);
        int totalPages = (int) Math.ceil((double) totalElements / size);

        String url = buildApiUrl(ingredients, startIdx, endIdx);
        System.out.println("url: " + url);
        RecipeResponseDTO.RecipeApiResponse apiResponse = restTemplate.getForObject(url, RecipeResponseDTO.RecipeApiResponse.class);

        List<RecipeResponseDTO.RecipeResponse> recipes = apiResponse.getRecipes().stream()
                .map(recipe -> RecipeResponseDTO.RecipeResponse.builder()
                        .recipeName(recipe.getRcpNm())
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

    // 즐겨찾기한 레시피 목록
    @Override
    @Transactional
    public RecipeResponseDTO.RecipePageResponse getFavoriteRecipes(Member member, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<MemberRecipe> memberRecipesPage = memberRecipeRepository.findByMember(member, pageable);

        List<RecipeResponseDTO.RecipeResponse> recipes = memberRecipesPage.stream()
                .map(MemberRecipe::getRecipeName)
                .map(this::fetchRecipeDetailFromApi)
                .filter(Objects::nonNull)  // null 응답 제거
                .collect(Collectors.toList());

        return RecipeResponseDTO.RecipePageResponse.builder()
                .page(page)
                .size(size)
                .totalPages(memberRecipesPage.getTotalPages())
                .totalElements(memberRecipesPage.getTotalElements())
                .recipes(recipes)
                .build();
    }

    // 즐겨찾기 수가 많은 레시피 목록
    @Override
    @Transactional
    public RecipeResponseDTO.RecipePageResponse getPopularRecipes(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        // 즐겨찾기 수가 많은 레시피 가져옴
        Page<Object[]> popularRecipesPage = memberRecipeRepository.findPopularRecipes(pageable);

        // 각 레시피에 대한 상세 정보
        List<RecipeResponseDTO.RecipeResponse> recipes = popularRecipesPage.stream()
                .map(record -> {
                    String recipeName = (String) record[0];
                    long favoriteCount = (long) record[1];
                    return fetchRecipeDetailWithFavoriteCount(recipeName, favoriteCount);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return RecipeResponseDTO.RecipePageResponse.builder()
                .page(page)
                .size(size)
                .totalPages(popularRecipesPage.getTotalPages())
                .totalElements(popularRecipesPage.getTotalElements())
                .recipes(recipes)
                .build();
    }

    private RecipeResponseDTO.RecipeResponse fetchRecipeDetailWithFavoriteCount(String recipeName, long favoriteCount) {
        String url = buildApiUrl(recipeName);

        RecipeResponseDTO.RecipeApiResponse apiResponse = restTemplate.getForObject(url, RecipeResponseDTO.RecipeApiResponse.class);

        if (apiResponse == null || apiResponse.getRecipes() == null || apiResponse.getRecipes().isEmpty()) {
            return null;
        }

        RecipeResponseDTO.RecipeApiResponse.Recipe recipe = apiResponse.getRecipes().get(0);

        return RecipeResponseDTO.RecipeResponse.builder()
                .recipeName(recipe.getRcpNm())
                .imageUrl(recipe.getAttFileNoMk())
                .favoriteCount(favoriteCount)
                .build();
    }

    private RecipeResponseDTO.RecipeResponse fetchRecipeDetailFromApi(String recipeName) {
        String url = buildApiUrl(recipeName);

        RecipeResponseDTO.RecipeApiResponse apiResponse = restTemplate.getForObject(url, RecipeResponseDTO.RecipeApiResponse.class);
        if (apiResponse == null || apiResponse.getRecipes() == null || apiResponse.getRecipes().isEmpty()) {
            return null;
        }

        RecipeResponseDTO.RecipeApiResponse.Recipe recipe = apiResponse.getRecipes().get(0);

        return RecipeResponseDTO.RecipeResponse.builder()
                .recipeName(recipe.getRcpNm())
                .imageUrl(recipe.getAttFileNoMk())
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

    // 기본 URL 생성
    private String buildApiUrl(String recipeName) {
        String quotedName = "\"" + recipeName + "\"";
        String baseUrl = "http://openapi.foodsafetykorea.go.kr/api/" + keyId + "/COOKRCP01/json/1/1";
        return baseUrl + "/RCP_NM=" + quotedName;
    }

    // 페이징용 URL 생성
    private String buildApiUrl(String ingredients, int startIdx, int endIdx) {

        String quotedName = "\"" + ingredients + "\"";
        String baseUrl = "http://openapi.foodsafetykorea.go.kr/api/" + keyId + "/COOKRCP01/json/" + startIdx + "/" + endIdx;
        return baseUrl + "/RCP_PARTS_DTLS=" + quotedName;

        /*return UriComponentsBuilder.fromHttpUrl("http://openapi.foodsafetykorea.go.kr/api")
                .pathSegment(keyId, "COOKRCP01", "json", String.valueOf(startIdx), String.valueOf(endIdx))
                .queryParam("RCP_PARTS_DTLS", ingredients)
                .toUriString();*/
    }

    private String cleanIngredients(String ingredients) {
        // 처음 콤마 앞부분과 콤마 제거
        String cleanedIngredients = ingredients.replaceFirst("^[^,]+,\\s*", "").trim();

        // 개행으로 감싸진 불필요한 섹션 제거 후 줄바꿈을 콤마로 변환
        cleanedIngredients = cleanedIngredients.replaceAll("\n[^,\n]+\n", "\n")
                .replace("\n", ", ")
                .trim();

        return cleanedIngredients;
    }



    private List<String> cleanManualSteps(List<String> manualSteps) {
        return manualSteps.stream()
                .map(step -> step.replaceAll("[a-zA-Z]$", "").trim())  // 마지막 알파벳 제거
                .map(step -> step.replaceAll("^[0-9]+\\.\\s*", "").trim())
                .collect(Collectors.toList());
    }
}
