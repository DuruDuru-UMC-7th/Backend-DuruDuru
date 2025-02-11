package com.backend.DuruDuru.global.web.dto.Recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RecipePageResponse {
        private int page;
        private int size;
        private int totalPages;
        private long totalElements;
        private List<RecipeResponse> recipes;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RecipeResponse {
        private String recipeName;
        private String imageUrl;
    }
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RecipeDetailResponse {
        private long favoriteCount; // 즐겨찾기 수
        private String recipeName; // 메뉴명
        private String cookingMethod; // 조리방법
        private String recipeType; // 요리종류
        private String ingredients; // 재료정보
        private String imageUrl; // 이미지 경로(대)
        private List<String> manualSteps; // 만드는 법 단계 목록
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RecipeApiResponse {

        @JsonProperty("COOKRCP01")
        private RecipeData data;

        public List<Recipe> getRecipes() {
            return data != null ? data.getRow() : null;
        }

        static class RecipeData {
            private List<Recipe> row;

            public List<Recipe> getRow() {
                return row;
            }
        }

        @Getter
        public static class Recipe {
            @JsonProperty("RCP_SEQ")
            private String rcpSeq;           // 일련번호

            @JsonProperty("RCP_NM")
            private String rcpNm;            // 메뉴명

            @JsonProperty("RCP_WAY2")
            private String rcpWay2;          // 조리방법

            @JsonProperty("RCP_PAT2")
            private String rcpPat2;          // 요리종류

            @JsonProperty("RCP_PARTS_DTLS")
            private String rcpPartsDtls;     // 재료정보

            @JsonProperty("ATT_FILE_NO_MAIN")
            private String attFileNoMain;

            @JsonProperty("ATT_FILE_NO_MK")
            private String attFileNoMk;

            @JsonProperty("MANUAL01")
            private String manual01;

            @JsonProperty("MANUAL02")
            private String manual02;

            @JsonProperty("MANUAL03")
            private String manual03;

            @JsonProperty("MANUAL04")
            private String manual04;

            @JsonProperty("MANUAL05")
            private String manual05;

            @JsonProperty("MANUAL06")
            private String manual06;

            @JsonProperty("MANUAL07")
            private String manual07;

            @JsonProperty("MANUAL08")
            private String manual08;

            @JsonProperty("MANUAL09")
            private String manual09;

            @JsonProperty("MANUAL10")
            private String manual10;

            @JsonProperty("MANUAL11")
            private String manual11;

            @JsonProperty("MANUAL12")
            private String manual12;

            @JsonProperty("MANUAL13")
            private String manual13;

            @JsonProperty("MANUAL14")
            private String manual14;

            @JsonProperty("MANUAL15")
            private String manual15;

            @JsonProperty("MANUAL16")
            private String manual16;

            @JsonProperty("MANUAL17")
            private String manual17;

            @JsonProperty("MANUAL18")
            private String manual18;

            @JsonProperty("MANUAL19")
            private String manual19;

            @JsonProperty("MANUAL20")
            private String manual20;

            public List<String> getManualSteps() {
                return List.of(manual01, manual02, manual03, manual04, manual05, manual06, manual07, manual08, manual09, manual10,
                                manual11, manual12, manual13, manual14, manual15, manual16, manual17, manual18, manual19, manual20)
                        .stream()
                        .filter(step -> step != null && !step.isBlank())  // null이거나 빈 문자열인 경우 제외
                        .collect(Collectors.toList());            }
        }
    }
}
