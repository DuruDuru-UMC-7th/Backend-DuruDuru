package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_category_id", nullable = false)
    private MajorCategory majorCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "minor_category_id", nullable = false)
    private MinorCategory minorCategory;


    public void setIngredient(Ingredient ingredient) {
        if (this.ingredient != null) {
            this.ingredient.getIngredientCategoryList().remove(this);
        }
        this.ingredient = ingredient;

        ingredient.getIngredientCategoryList().add(this);
    }

    public void setMajorCategory(MajorCategory majorCategory) {
        if (this.majorCategory != null) {
            this.majorCategory.getIngredientCategoryList().remove(this);
        }
        this.majorCategory = majorCategory;

        if (majorCategory != null) {
            majorCategory.getIngredientCategoryList().add(this);
        }
    }

    public void setMinorCategory(MinorCategory minorCategory) {
        if (this.minorCategory != null) {
            this.minorCategory.getIngredientCategoryList().remove(this);
        }
        this.minorCategory = minorCategory;

        if (minorCategory != null) {
            minorCategory.getIngredientCategoryList().add(this);
        }
    }





}