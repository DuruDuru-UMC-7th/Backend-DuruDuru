package com.backend.DuruDuru.global.web.dto.Trade;

import com.backend.DuruDuru.global.domain.entity.TradeImg;
import com.backend.DuruDuru.global.domain.enums.Status;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import lombok.Getter;

public class TradeRequestDTO {

    @Getter
    public static class CreateTradeRequestDTO {
        Long ingredientCount;
        String title;
        String body;
        TradeType tradeType;
        // TradeImg[] tradeImg;
    }

    @Getter
    public static class UpdateTradeRequestDTO {
        Long ingredientCount;
        String title;
        String body;
        Status status;
        TradeType tradeType;
        // TradeImg[] tradeImg;
    }
}
