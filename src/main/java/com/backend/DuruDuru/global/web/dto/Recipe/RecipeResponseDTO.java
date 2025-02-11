package com.backend.DuruDuru.global.web.dto.Recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

public class RecipeResponseDTO {

//    @Getter
//    @Builder
//    @AllArgsConstructor(access = AccessLevel.PROTECTED)
//    @NoArgsConstructor(access = AccessLevel.PROTECTED)
//    public static class RecipeResponse {
//        private Long recipeId;
//        private String title;
//        private String content;
//    }

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
        private String recipeId;
        private String imageUrl;
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
            private String rcpSeq;

            @JsonProperty("ATT_FILE_NO_MAIN")
            private String attFileNoMain;

        }
    }
}
