package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Fridge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ingredientKind;

    private Long ingredientCount;

    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();
}
