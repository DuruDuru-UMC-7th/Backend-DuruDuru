package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class IngredientCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_category_id", nullable = false, columnDefinition = "bigint")
    private Long ingredientCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "minor_category", nullable = false, columnDefinition = "varchar(50)")
    private MinorCategory minorCategory;

    @Column(name = "major_category", nullable = false, columnDefinition = "varchar(50)")
    private MajorCategory majorCategory;

    public void setIngredient(Ingredient ingredient) {
        if (this.ingredient != null) {
            this.ingredient.getIngredientCategoryList().remove(this);
        }
        this.ingredient = ingredient;

        ingredient.getIngredientCategoryList().add(this);
    }
}