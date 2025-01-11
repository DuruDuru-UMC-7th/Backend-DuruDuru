package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import com.backend.DuruDuru.global.domain.enums.StorageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private Long id;

    @Column(length = 200)
    private String ingredientName;

    private Long count;

    private Long weight;

    private LocalDateTime purchaseDate;

    private LocalDateTime expiryDate;

    private LocalDateTime sellByDate;

    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    private Long price;

    @Column(length = 200)
    private String ingredientImageUrl;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private List<Trade> trades = new ArrayList<>();

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id")
    private Fridge fridge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
