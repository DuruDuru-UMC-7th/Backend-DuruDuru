package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TradeQueryService {
    // 품앗이 게시글 상세 조회
    Trade getTrade(Long tradeId);
    // 나눔, 조회별 게시글 조회
    Page<Trade> getNearTradesByType(Long memberId, TradeType tradeType, Integer page, Integer size);
}
