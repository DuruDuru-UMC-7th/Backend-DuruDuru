package com.backend.DuruDuru.global.web.dto.Recipe;

import lombok.*;

import java.util.List;

public class RecipeResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RecipeResponse {
        private Long recipeId;
        private String title;
        private String content;
    }

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
}
