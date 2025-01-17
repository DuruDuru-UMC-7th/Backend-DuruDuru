package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import com.backend.DuruDuru.global.domain.enums.StorageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Ingredient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id", nullable = false, columnDefinition = "bigint")
    private Long ingredientId;

    @Column(name = "ingredient_name", length = 200, nullable = false)
    private String ingredientName;

    @Column(name = "count", nullable = false, columnDefinition = "bigint")
    private Long count;

    @Column(name = "purchase_date", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime purchaseDate;

    @Column(name = "expiry_date", nullable = true, columnDefinition = "timestamp")
    private LocalDateTime expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", nullable = true, columnDefinition = "varchar(50)")
    private StorageType storageType;

    @Column(name = "ingredient_image_url", length = 200, nullable = true)
    private String ingredientImageUrl;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trade> trades = new ArrayList<>();

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id", nullable = false)
    private Fridge fridge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private IngredientImg ingredientImg;
}
