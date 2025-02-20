package com.backend.DuruDuru.global.service.RecipeService;

import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.RecipeException;
import com.backend.DuruDuru.global.apiPayload.exception.handler.MemberException;
import com.backend.DuruDuru.global.domain.entity.Fridge;
import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.MemberRecipe;
import com.backend.DuruDuru.global.repository.FridgeRepository;
import com.backend.DuruDuru.global.repository.IngredientRepository;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeCommandServiceImpl implements RecipeCommandService {

    private final MemberRecipeRepository memberRecipeRepository;
    private final RestTemplate restTemplate;
    private final FridgeRepository fridgeRepository;

    @Value("${api.foodsafety.keyId}")
    private String keyId;
    private final String API_URL = "http://openapi.foodsafetykorea.go.kr/api/{apiKey}/COOKRCP01/json";


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

    @Override
    public RecipeResponseDTO.RecipePageResponse searchRecipes(String query, int page, int size) {
        int startIdx = (page - 1) * size + 1;
        int endIdx = page * size;

        String url = API_URL.replace("{apiKey}", keyId) + "/" + startIdx + "/" + endIdx + "/RCP_NM=" + query;
        RecipeResponseDTO.RecipeApiResponse apiResponse = restTemplate.getForObject(url, RecipeResponseDTO.RecipeApiResponse.class);

        System.out.println("url: " + url);
        if (apiResponse == null || apiResponse.getRecipes() == null) {
            return RecipeResponseDTO.RecipePageResponse.builder()
                    .page(page)
                    .size(size)
                    .totalPages(0)
                    .totalElements(0)
                    .recipes(Collections.emptyList())
                    .build();
        }

        List<RecipeResponseDTO.RecipeResponse> recipes = apiResponse.getRecipes().stream()
                .map(recipe -> RecipeResponseDTO.RecipeResponse.builder()
                        .recipeName(recipe.getRcpNm())
                        .imageUrl(recipe.getAttFileNoMain())
                        .build())
                .collect(Collectors.toList());

        int totalElements = recipes.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return RecipeResponseDTO.RecipePageResponse.builder()
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .recipes(recipes)
                .build();
    }
    // 식재료 기반 레시피 추천
    @Override
    @Transactional
    public RecipeResponseDTO.RecipePageResponse searchRecipesByIngredient(String ingredients, int page, int size) {
        int startIdx = (page - 1) * size + 1;
        int endIdx = page * size;

        // 전체 항목 개수 계산
        long totalElements = getTotalElements(ingredients);
        int totalPages = (int) Math.ceil((double) totalElements / size);

        String url = buildApiUrl(ingredients, startIdx, endIdx);
        System.out.println("url: " + url);
        RecipeResponseDTO.RecipeApiResponse apiResponse = restTemplate.getForObject(url, RecipeResponseDTO.RecipeApiResponse.class);


        if (apiResponse == null || apiResponse.getRecipes() == null) {
            return RecipeResponseDTO.RecipePageResponse.builder()
                    .page(page)
                    .size(size)
                    .totalPages(0)
                    .totalElements(0)
                    .recipes(Collections.emptyList())
                    .build();
        }
        
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

    // 남은 재료로 뚝딱
    @Override
    public RecipeResponseDTO.RecipePageResponse getRecipesWithIngredientInfo(Member member, int page, int size) {
        // 사용자 냉장고의 식재료 조회
        Fridge fridge = fridgeRepository.findByMember(member)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        List<Ingredient> ingredients = fridge.getIngredients();

        // 사용자의 남은 식재료 이름 리스트 추출
        List<String> myIngredientNames = ingredients.stream()
                .map(Ingredient::getIngredientName)
                .collect(Collectors.toList());

        List<RecipeResponseDTO.RecipeResponse> recipes = new ArrayList<>();

        // 재료가 없으면 빈 응답 반환
        if (myIngredientNames.isEmpty()) {
            return RecipeResponseDTO.RecipePageResponse.builder()
                    .page(page)
                    .size(size)
                    .totalPages(0)
                    .totalElements(0)
                    .recipes(recipes)
                    .build();
        }

        // 식재료를 ,로 결합하여 Open API 요청 URL 생성
        String joinedIngredients = String.join(",", myIngredientNames);
        String url = buildApiUrl(joinedIngredients, 1, 50);
        RecipeResponseDTO.RecipeApiResponse apiResponse = restTemplate.getForObject(url, RecipeResponseDTO.RecipeApiResponse.class);

        // 응답된 레시피 데이터 필터링
        if (apiResponse != null && apiResponse.getRecipes() != null) {
            for (RecipeResponseDTO.RecipeApiResponse.Recipe recipe: apiResponse.getRecipes()) {
                List<String> recipeIngredients = extractIngredients(recipe.getRcpPartsDtls());
                List<String> availableIngredients = new ArrayList<>();
                List<String> missingIngredients = new ArrayList<>();

                for (String recipeIngredient: recipeIngredients) {
                    if (myIngredientNames.contains(recipeIngredient)) {
                        availableIngredients.add(recipeIngredient);
                    } else {
                        missingIngredients.add(recipeIngredient);
                    }
                }

                recipes.add(RecipeResponseDTO.RecipeResponse.builder()
                        .recipeName(recipe.getRcpNm())
                        .imageUrl(recipe.getAttFileNoMain())
                        .availableIngredients(availableIngredients)
                        .missingIngredients(missingIngredients)
                        .build());
            }
        }

        int totalElements = recipes.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        List<RecipeResponseDTO.RecipeResponse> pagedRecipes = recipes.stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());

        return RecipeResponseDTO.RecipePageResponse.builder()
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .recipes(pagedRecipes)
                .build();
    }


    private List<String> extractIngredients(String rawIngredients) {
        return Arrays.stream(rawIngredients.split("[,\n]"))  // 쉼표(,) 또는 개행(\n)으로 분리
                .map(String::trim)
                .map(ingredient -> ingredient.replaceAll("\\(.*?\\)", "").trim()) // 괄호 제거
                .map(ingredient -> ingredient.replaceAll("\\d+(\\.\\d+)?[gGmlLkg개oz컵]*", "").trim()) // 소수점 포함 숫자 제거
                .filter(ingredient -> !ingredient.isEmpty()) // 빈 문자열 제거
                .collect(Collectors.toList());
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
        String baseUrl = "http://openapi.foodsafetykorea.go.kr/api/" + keyId + "/COOKRCP01/json/1/1";
        return baseUrl + "/RCP_NM=" + recipeName;
    }

    // 페이징용 URL 생성
    private String buildApiUrl(String ingredients, int startIdx, int endIdx) {

        String baseUrl = "http://openapi.foodsafetykorea.go.kr/api/" + keyId + "/COOKRCP01/json/" + startIdx + "/" + endIdx;
        return baseUrl + "/RCP_PARTS_DTLS=" + ingredients;
    }

    private String cleanIngredients(String ingredients) {
        return Arrays.stream(ingredients.split("[,\n]"))
                .map(String::trim)
                .map(ingredient -> ingredient.replaceAll("[●◆■▪◦•]", "").trim()) // 특수문자 제거
                .map(ingredient -> ingredient.replaceAll("\\(.*?\\)", "").trim()) // 괄호 제거
                .map(ingredient -> ingredient.replaceAll("\\d+(\\.\\d+)?[gGmlLkg개oz컵큰술작은술]+", "").trim()) // 숫자 + 단위 제거
                .map(ingredient -> ingredient.replaceAll("^[^:]+:\\s*", "").trim()) // "이름 : " 패턴 제거
                .filter(ingredient -> !ingredient.isEmpty())
                .collect(Collectors.joining(", "));
    }


    private List<String> cleanManualSteps(List<String> manualSteps) {
        return manualSteps.stream()
                .map(step -> step.replaceAll("\n", " "))  // 개행문자를 공백으로 변환
                .map(step -> step.replaceAll("\\s+", " ").trim())  // 중복 공백 제거
                .map(step -> step.replaceAll("[a-zA-Z]$", "").trim())  // 마지막 알파벳 제거
                .map(step -> step.replaceAll("^[0-9]+\\.\\s*", "").trim()) // 앞쪽 숫자 제거
                .collect(Collectors.toList());
    }

}
