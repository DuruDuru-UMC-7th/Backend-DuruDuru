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
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Trade extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id", nullable = false, columnDefinition = "bigint")
    private Long tradeId;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(200)")
    private String title;

    @Column(name = "body", nullable = false, columnDefinition = "varchar(500)")
    private String body;

    @Column(name = "ingredient_count", nullable = false, columnDefinition = "bigint")
    private Long ingredientCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(50)")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_type", nullable = false, columnDefinition = "varchar(50)")
    private TradeType tradeType;

    @Column(name = "latitude", nullable = false, columnDefinition = "double precision")
    private Double latitude;

    @Column(name = "longitude", nullable = false, columnDefinition = "double precision")
    private Double longitude;

    @Column(name = "eupmyeondong", nullable = false, columnDefinition = "varchar(50)")
    private String eupmyeondong;

    @Column(name = "like_count", columnDefinition = "bigint")
    private Long likeCount = 0L;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChattingRoom> chattingRooms = new ArrayList<>();

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TradeImg> tradeImgs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeTrade> likeTrades = new ArrayList<>();

    public void updateTrade(Trade trade) {
        this.title = trade.getTitle();
        this.body = trade.getBody();
        this.ingredientCount = trade.getIngredientCount();
        this.status = trade.getStatus();
        this.tradeType = trade.getTradeType();
    }

    public void addLikeTrades(LikeTrade likeTrade) {
        this.likeTrades.add(likeTrade);
        if (likeTrade.getTrade() != this) {
            likeTrade.setTrade(this);
        }
    }

    public void increaseLikeCount() {
        likeCount++;
    }

    public void decreaseLikeCount() {
        likeCount--;
    }
}
