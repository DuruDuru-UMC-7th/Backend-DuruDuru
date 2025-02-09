package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.enums.TradeType;

import java.util.List;

public interface TradeQueryService {
    // 품앗이 게시글 상세 조회
    Trade getTrade(Long tradeId);
    // 나눔, 조회별 게시글 조회
    List<Trade> getNearTradesByType(Long memberId, TradeType tradeType);
    // 멤버별 전체 품앗이 게시글 리스트 조회
    List<Trade> getAllTradesByMember(Long memberId);
    // 멤버별 활성화 품앗이 게시글 리스트 조회
    List<Trade> getActiveTradesByMember(Long memberId);
}
