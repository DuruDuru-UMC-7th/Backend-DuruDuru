package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.enums.Status;
import com.backend.DuruDuru.global.web.dto.Trade.TradeRequestDTO;
import com.backend.DuruDuru.global.web.dto.Trade.TradeResponseDTO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TradeConverter {

    public static TradeResponseDTO.TradeDetailResultDTO toTradeDetailDTO(Trade trade) {
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
                .tradeImgs(trade.getTradeImgs())
                .createdAt(trade.getCreatedAt())
                .updatedAt(trade.getUpdatedAt())
                .build();
    }

    public static Trade toCreateTrade(TradeRequestDTO.CreateTradeRequestDTO request, Member member, Ingredient ingredient) {
        // 요청한 식재료의 개수가 현재 재고보다 적을 경우
        if(request.getIngredientCount() > ingredient.getCount()) {
            throw new IllegalArgumentException("요청한 식재료의 개수가 현재 재고보다 많습니다.");
        }
        // Member에 Town 정보가 없을 경우
        if(member.getTown() == null) {
            throw new IllegalArgumentException("Member가 Town 정보를 가지고 있지 않습니다.");
        }

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
                .thumbnailImgUrl((trade.getTradeImgs() != null && !trade.getTradeImgs().isEmpty())
                        ? trade.getTradeImgs().get(0).getTradeImgUrl()
                        : null)      // TradeImgs의 첫 번째 이미지를 thumbnailImg로 자동 등록
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
}
