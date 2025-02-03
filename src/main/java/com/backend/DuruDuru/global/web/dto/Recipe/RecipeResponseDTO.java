package com.backend.DuruDuru.global.web.dto.Recipe;

import lombok.*;

public class RecipeResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DetailResponse {
        private Long recipeId;
        private String title;
        private String content;
    }

}
