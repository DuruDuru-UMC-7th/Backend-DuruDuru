package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.web.dto.Trade.TradeRequestDTO;

public interface TradeCommandService {

    // Trade 엔티티를 저장하는 메서드
    Trade createTrade(Long memberId, Long ingredientId, TradeRequestDTO.CreateTradeRequestDTO request);
    // 품앗이 게시글 상세 조회
    Trade getTrade(Long tradeId);
    // 품앗이 게시글 수정
    Trade updateTrade(Long memberId, Long tradeId, TradeRequestDTO.UpdateTradeRequestDTO request);
}
