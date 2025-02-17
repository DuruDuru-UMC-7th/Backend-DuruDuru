package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.LikeTrade;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeTradeRepository extends JpaRepository<LikeTrade, Long> {
    // 멤버와 품앗이로 찜하기 조회
    LikeTrade findByMemberAndTrade(Member member, Trade trade);
    boolean existsByMemberAndTrade(Member member, Trade trade);
}
