package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.domain.entity.LikeTrade;
import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.web.dto.Trade.TradeRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TradeCommandService {
    // Trade 엔티티를 저장하는 메서드
    Trade createTrade(Long memberId, Long ingredientId, TradeRequestDTO.CreateTradeRequestDTO request, List<MultipartFile> tradeImgs);
    // 품앗이 게시글 삭제
    void deleteTrade(Long memberId, Long tradeId);
    // 품앗이 게시글 수정
    Trade updateTrade(Long memberId, Long tradeId, TradeRequestDTO.UpdateTradeRequestDTO request);
    // 찜하기 등록
    LikeTrade createLike(Long memberId, Long tradeId);
}
