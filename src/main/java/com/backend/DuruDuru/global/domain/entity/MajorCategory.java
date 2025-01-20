package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MajorCategory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_category_id", nullable = false, columnDefinition = "bigint")
    private Long majorCategoryId;

    @Column(name = "major_category_name", nullable = false, columnDefinition = "varchar(200)")
    private String majorCategoryName;

//    @ManyToMany(mappedBy = "majorCategoryList")
//    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "majorCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientCategory> ingredientCategoryList = new ArrayList<>();

    @Builder
    public MajorCategory(String majorCategoryName) {
        this.majorCategoryName = majorCategoryName;
    }
}

