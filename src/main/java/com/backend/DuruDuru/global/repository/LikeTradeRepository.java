package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.LikeTrade;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeTradeRepository extends JpaRepository<LikeTrade, Long> {

    LikeTrade findByMemberAndTrade(Member member, Trade trade);
}
