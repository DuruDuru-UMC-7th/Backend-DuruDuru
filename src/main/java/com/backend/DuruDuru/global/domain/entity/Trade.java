package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import com.backend.DuruDuru.global.domain.enums.Status;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Trade extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String title;

    @Column(length = 500)
    private String body;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL)
    private List<ChattingRoom> chattingRooms = new ArrayList<>();

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL)
    private List<TradeImg> tradeImgs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
}
