package com.backend.DuruDuru.global.web.dto.Trade;

import com.backend.DuruDuru.global.domain.enums.Status;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class TradeResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateTradeResultDTO {
        Long tradeId;
        Long memberId;
        Long ingredientId;
        Long ingredientCount;
        LocalDateTime expiryDate;
        String title;
        String body;
        Status status;
        TradeType tradeType;
        LocalDateTime createdAt;
        // String[] tradeImgUrls;
    }
}
