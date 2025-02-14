package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.enums.TradeType;

import java.util.List;

public interface TradeQueryService {
    // 품앗이 게시글 상세 조회
    Trade getTrade(Member member, Long tradeId);
    // 가까운 게시글 중에서 나눔, 조회별 게시글 조회
    List<Trade> getNearTradesByType(Member member, TradeType tradeType);
    // 멤버별 전체 품앗이 게시글 리스트 조회
    List<Trade> getAllTradesByMember(Member member);
    // 멤버별 활성화 품앗이 게시글 리스트 조회
    List<Trade> getActiveTradesByMember(Member member);
    // 근처 품앗이 최신 등록순 조회
    List<Trade> getRecentTrades(Member member);
    // 근처 품앗이 소비기한 임박순 조회
    List<Trade> getNearExpiryTrade(Member member);
    // 근처 품앗이 소비기한 여유순 조회
    List<Trade> getFarExpiryTrade(Member member);
    // 찜하기한 게시글 조회
    List<Trade> getAllLikeTradesByMember(Member member);
    // 다른 품앗이 조회
    List<Trade> getOtherTrade(Member member, Long tradeId);
}
