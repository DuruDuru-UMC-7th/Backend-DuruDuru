package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
public class MinorCategory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "minor_category_id", nullable = false, columnDefinition = "bigint")
    private Long minorCategoryId;

    @Column(name = "minor_category_name", nullable = false, columnDefinition = "varchar(200)")
    private String minorCategoryName;

//    @ManyToMany(mappedBy = "minorCategoryList")
//    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "minorCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientCategory> ingredientCategoryList = new ArrayList<>();

    @Builder
    public MinorCategory(String minorCategoryName) {
        this.minorCategoryName = minorCategoryName;
    }
}
