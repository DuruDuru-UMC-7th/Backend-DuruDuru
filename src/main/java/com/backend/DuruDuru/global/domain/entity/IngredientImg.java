package com.backend.DuruDuru.global.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class IngredientImg {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_img_id", nullable = false, columnDefinition = "bigint")
    private Long ingredientImgId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "ingredient_img_url", length = 200, nullable = false)
    private String ingredientImgUrl;
}
