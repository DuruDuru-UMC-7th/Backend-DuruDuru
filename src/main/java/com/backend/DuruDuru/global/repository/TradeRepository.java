package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
