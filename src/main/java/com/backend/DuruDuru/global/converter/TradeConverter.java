package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.LikeTrade;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.enums.Status;
import com.backend.DuruDuru.global.web.dto.Trade.TradeRequestDTO;
import com.backend.DuruDuru.global.web.dto.Trade.TradeResponseDTO;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TradeConverter {

    public static TradeResponseDTO.TradeDetailResultDTO toTradeDetailDTO(Trade trade, boolean isLiked) {
        return TradeResponseDTO.TradeDetailResultDTO.builder()
                .tradeId(trade.getTradeId())
                .memberId(trade.getMember().getMemberId())
                .nickName(trade.getMember().getNickName())
                .ingredientId(trade.getIngredient().getIngredientId())
                .ingredientCount(trade.getIngredientCount())
                .expiryDate(trade.getIngredient().getExpiryDate())
                .title(trade.getTitle())
                .body(trade.getBody())
                .eupmyeondong(trade.getEupmyeondong())
                .status(trade.getStatus())
                .tradeType(trade.getTradeType())
                .liked(isLiked)
                .likeCount(trade.getLikeCount())
                .tradeImgs(trade.getTradeImgs())
                .createdAt(trade.getCreatedAt())
                .updatedAt(trade.getUpdatedAt())
                .build();
    }

    public static Trade toCreateTrade(TradeRequestDTO.CreateTradeRequestDTO request, Member member, Ingredient ingredient) {
        return Trade.builder()
                .member(member)
                .ingredient(ingredient)
                .ingredientCount(request.getIngredientCount())
                .title(ingredient.getIngredientName())      // 식재료 이름으로 제목 자동 설정
                .body(request.getBody())
                .latitude(member.getTown().getLatitude())
                .longitude(member.getTown().getLongitude())
                .eupmyeondong(member.getTown().getEupmyeondong())       // 읍면동 정보 저장
                .status(Status.ACTIVE)
                .tradeType(request.getTradeType())
                .tradeImgs(new ArrayList<>())
                .likeCount(0L)
                .build();
    }

    public static TradeResponseDTO.TradePreviewDTO tradePreviewDTO(Trade trade) {
        return TradeResponseDTO.TradePreviewDTO.builder()
                .tradeId(trade.getTradeId())
                .memberId(trade.getMember().getMemberId())
                .ingredientId(trade.getIngredient().getIngredientId())
                .ingredientCount(trade.getIngredientCount())
                .expiryDate(trade.getIngredient().getExpiryDate())
                .title(trade.getTitle())
                .eupmyeondong(trade.getEupmyeondong())
                .status(trade.getStatus())
                .tradeType(trade.getTradeType())
                .likeCount(trade.getLikeCount())
                .thumbnailImgUrl(
                        (trade.getTradeImgs() != null && !trade.getTradeImgs().isEmpty())
                        ? trade.getTradeImgs().get(0).getTradeImgUrl() : null)      // TradeImgs의 첫 번째 이미지를 thumbnailImg로 자동 등록
                .createdAt(trade.getCreatedAt())
                .updatedAt(trade.getUpdatedAt())
                .build();
    }

    public static TradeResponseDTO.TradePreviewListDTO toTradePreviewListDTO(List<Trade> tradeList) {
        List<TradeResponseDTO.TradePreviewDTO> tradePreViewDTOList = tradeList.stream()
                .map(TradeConverter::tradePreviewDTO).collect(Collectors.toList());

        return TradeResponseDTO.TradePreviewListDTO.builder()
                .totalCount(tradePreViewDTOList.size())
                .tradeList(tradePreViewDTOList)
                .build();
    }

    public static LikeTrade toLikeTrade(Member member, Trade trade) {
        return LikeTrade.builder()
                .member(member)
                .trade(trade)
                .build();
    }

    public static TradeResponseDTO.LikeTradeResultDTO toLikeTradeResultDTO(LikeTrade likeTrade) {
        return TradeResponseDTO.LikeTradeResultDTO.builder()
                .memberId(likeTrade.getMember().getMemberId())
                .tradeId(likeTrade.getTrade().getTradeId())
                .likeCount(likeTrade.getTrade().getLikeCount())
                .build();
    }

    public static TradeResponseDTO.LikeCountResultDTO toLikeCountResultDTO(Trade trade) {
        return TradeResponseDTO.LikeCountResultDTO.builder()
                .tradeId(trade.getTradeId())
                .likeCount(trade.getLikeCount())
                .build();
    }

    public static TradeResponseDTO.IsLikeResultDTO toIsLikedResultDTO(Member member, Long tradeId, boolean isLiked) {
        return TradeResponseDTO.IsLikeResultDTO.builder()
                .memberId(member.getMemberId())
                .tradeId(tradeId)
                .liked(isLiked)
                .build();
    }
}
