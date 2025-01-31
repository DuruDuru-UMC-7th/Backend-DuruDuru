package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.web.dto.Trade.TradeRequestDTO;
import com.backend.DuruDuru.global.web.dto.Trade.TradeResponseDTO;

public class TradeConverter {

    public static TradeResponseDTO.TradeDetailResultDTO toTradeResultDTO(Trade trade) {
        return TradeResponseDTO.TradeDetailResultDTO.builder()
                .tradeId(trade.getTradeId())
                .memberId(trade.getMember().getMemberId())
                .ingredientId(trade.getIngredient().getIngredientId())
                .ingredientCount(trade.getIngredientCount())
                .title(trade.getTitle())
                .body(trade.getBody())
                .status(trade.getStatus())
                .tradeType(trade.getTradeType())
                //.tradeImgUrls(trade.getTradeImgs())
                .createdAt(trade.getCreatedAt())
                .updatedAt(trade.getUpdatedAt())
                .build();
    }

    public static Trade toCreateTrade(TradeRequestDTO.CreateTradeRequestDTO request, Member member, Ingredient ingredient) {
        // 요청한 식재료의 개수가 현재 재고보다 적을 경우
        if(request.getIngredientCount() < ingredient.getCount()) {
            throw new IllegalArgumentException("요청한 식재료의 개수가 현재 재고보다 적습니다.");
        }

        return Trade.builder()
                .member(member)
                .ingredient(ingredient)
                .title(request.getTitle())
                .body(request.getBody())
                .ingredientCount(request.getIngredientCount())
                .status(request.getStatus())
                .tradeType(request.getTradeType())
                //.tradeImgs(request.)
                .build();
    }
}
