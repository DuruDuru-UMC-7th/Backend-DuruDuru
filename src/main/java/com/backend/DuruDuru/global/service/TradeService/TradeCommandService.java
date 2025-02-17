package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.domain.entity.LikeTrade;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.web.dto.Trade.TradeRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TradeCommandService {
    // 품앗이 게시글 등록
    Trade createTrade(Member member, Long ingredientId, TradeRequestDTO.CreateTradeRequestDTO request);
    // 품앗이 게시글 삭제
    void deleteTrade(Member member, Long tradeId);
    // 품앗이 게시글 수정
    Trade updateTrade(Member member, Long tradeId, TradeRequestDTO.UpdateTradeRequestDTO request);
    // 찜하기 등록
    LikeTrade createLike(Member member, Long tradeId);
    // 찜하기 취소
    void deleteLike(Member member, Long tradeId);
    // 찜하기 여부 확인
    boolean isLiked(Member member, Long tradeId);
}
