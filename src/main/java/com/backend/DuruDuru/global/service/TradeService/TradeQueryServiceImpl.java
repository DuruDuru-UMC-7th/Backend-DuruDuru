package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import com.backend.DuruDuru.global.repository.MemberRepository;
import com.backend.DuruDuru.global.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeQueryServiceImpl implements TradeQueryService {

    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;

    private Trade findTradeById(Long tradeId) {
        return tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found. ID: " + tradeId));
    }

    // 품앗이 게시글 상세 조회
    @Override
    @Transactional
    public Trade getTrade(Long tradeId) {
        return findTradeById(tradeId);
    }

    @Override
    @Transactional
    public Trade[] getTradesByType(Long memberId, TradeType tradeType) {
        return new Trade[0];
    }

}
