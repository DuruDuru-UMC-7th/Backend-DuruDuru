package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    // 사용자의 전체 게시글 반환
    List<Trade> findAllByMemberOrderByUpdatedAtDesc(Member member);

    // 사용자의 활성화된 게시글 반환
    @Query(value = """
        SELECT * FROM trade
        WHERE member_id = :memberId AND (status = 'ACTIVE' OR status = 'PROCEEDING')
        ORDER BY updated_at DESC
        """, nativeQuery = true)
    List<Trade> findActiveTradesByMember(@Param("memberId") Long memberId);

    // 사용자와의 거리가 1km 이내인 게시글 리스트 반환
    @Query(value = """
        SELECT * FROM trade
        WHERE trade_type = :tradeType
        AND (status = 'ACTIVE' OR status = 'PROCEEDING')
        AND (6371 * acos(cos(radians(:memberLat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:memberLon)) + sin(radians(:memberLat)) * sin(radians(latitude)))) <= 1
        ORDER BY updated_at DESC
        """, nativeQuery = true)
    List<Trade> findNearbyTrades(
            @Param("memberLat") double memberLat,
            @Param("memberLon") double memberLon,
            @Param("tradeType") String tradeType
    );

}

