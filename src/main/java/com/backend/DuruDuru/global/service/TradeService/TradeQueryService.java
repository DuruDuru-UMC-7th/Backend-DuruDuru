package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.enums.TradeType;

public interface TradeQueryService {
    // 품앗이 게시글 상세 조회
    Trade getTrade(Long tradeId);
    // 나눔, 조회별 게시글 조회
    Trade[] getTradesByType(Long memberId, TradeType tradeType);
}
