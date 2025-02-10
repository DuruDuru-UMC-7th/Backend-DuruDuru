package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import com.backend.DuruDuru.global.domain.enums.StorageType;
import com.backend.DuruDuru.global.service.IngredientService.DateFormatter;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    @Column(name = "count", nullable = true, columnDefinition = "bigint")
    private Long count;

    @Column(name = "purchase_date", nullable = true, columnDefinition = "timestamp")
    private LocalDate purchaseDate;

    @Column(name = "expiry_date", nullable = true, columnDefinition = "timestamp")
    private LocalDate expiryDate;

    @Column(name = "d_day", nullable = false, columnDefinition = "bigint")
    private Long dDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", nullable = true, columnDefinition = "varchar(50)")
    private StorageType storageType;

    @Enumerated(EnumType.STRING)
    @Column(name = "major_category", nullable = true, columnDefinition = "varchar(50)")
    private MajorCategory majorCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "minor_category", nullable = true, columnDefinition = "varchar(50)")
    private MinorCategory minorCategory;

//    @Column(name = "ingredient_image_url", length = 200, nullable = true)
//    private String ingredientImageUrl;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = true)
    private Receipt receipt;

    @OneToOne(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private IngredientImg ingredientImg;

    @Builder
    public Ingredient(String ingredientName, Long count, LocalDate purchaseDate, LocalDate expiryDate, StorageType storageType, MajorCategory majorCategory, MinorCategory minorCategory, Fridge fridge, Member member, Receipt receipt, IngredientImg ingredientImg) {
        this.ingredientName = ingredientName;
        this.count = count;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
        this.storageType = storageType;
        this.majorCategory = majorCategory;
        this.minorCategory = minorCategory;
        this.fridge = fridge;
        this.member = member;
        this.receipt = receipt;
        this.ingredientImg = new IngredientImg(this, "");
        calculateDDay();
    }

//    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<IngredientCategory> ingredientCategoryList = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
    }

    public void setFridge(Fridge fridge) {
        this.fridge = fridge;
    }

    public void update(IngredientRequestDTO.UpdateIngredientDTO request){
        this.ingredientName = request.getIngredientName();
        this.count = request.getCount();
    }

    public void updateOCR(IngredientRequestDTO.UpdateOCRIngredientDTO request){
        this.ingredientName = request.getIngredientName();
        this.count = request.getCount();
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public void updateCategory(MajorCategory majorCategory, MinorCategory minorCategory) {
        this.majorCategory = majorCategory;
        this.minorCategory = minorCategory;
        calculateDDay(); // 카테고리 변경시 D-Day 다시 계산
    }

    public void setIngredientImg(IngredientImg ingredientImg) {
        if (ingredientImg == null) {
            this.ingredientImg = IngredientImg.builder()
                    .ingredientImgUrl("https://duruduru.s3.ap-northeast-2.amazonaws.com/76636494-cfa7-4b1b-8649-2eda45f1be8a")
                    .ingredient(this)
                    .build();
        } else {
            this.ingredientImg = ingredientImg;
            ingredientImg.setIngredient(this);
        }
    }


    public void calculateDDay() {
        LocalDate today = LocalDate.now();
        this.dDay = (this.expiryDate != null) ? ChronoUnit.DAYS.between(today, this.expiryDate) : Long.MAX_VALUE;
    }

    @PrePersist
    @PreUpdate
    public void updateDDay() {
        calculateDDay();
    }

    @Transient
    public String getFormattedDDay() {
        return DateFormatter.formatRemainingDays(this.dDay);
    }




}
