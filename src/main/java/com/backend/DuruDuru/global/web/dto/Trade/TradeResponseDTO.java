package com.backend.DuruDuru.global.web.dto.Trade;

import com.backend.DuruDuru.global.domain.entity.TradeImg;
import com.backend.DuruDuru.global.domain.enums.Status;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TradeResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TradeDetailResultDTO {
        Long tradeId;
        Long memberId;
        String nickName;
        Long ingredientId;
        Long ingredientCount;
        LocalDate expiryDate;
        String title;
        String body;
        String eupmyeondong;
        Status status;
        TradeType tradeType;
        Long likeCount;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
        List<TradeImg> tradeImgs;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TradePreviewDTO {
        Long tradeId;
        Long memberId;
        Long ingredientId;
        Long ingredientCount;
        LocalDate expiryDate;
        String title;
        String eupmyeondong;
        Status status;
        TradeType tradeType;
        Long likeCount;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
        String thumbnailImgUrl;
        // double distance;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TradePreviewListDTO {
        int totalCount;
        List<TradePreviewDTO> tradeList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LikeTradeResultDTO {
        Long memberId;
        Long tradeId;
        Long likeCount;
    }
}
