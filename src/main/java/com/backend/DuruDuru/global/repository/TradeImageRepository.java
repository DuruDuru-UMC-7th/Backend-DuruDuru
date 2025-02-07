package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.entity.TradeImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeImageRepository extends JpaRepository<TradeImg, Long> {
    Long trade(Trade trade);
}
