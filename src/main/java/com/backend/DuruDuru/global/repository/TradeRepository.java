package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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

    // 사용자가 찜하기한 게시글 반환
    @Query(value = """
        SELECT t.* FROM trade t
        JOIN like_trade l ON t.trade_id = l.trade_id
        WHERE l.member_id = :memberId
        """, nativeQuery = true)
    List<Trade> findLikeTradesByMember(@Param("memberId") Long memberId);

    // 품앗이 타입별로 사용자와의 거리가 1km 이내 게시글 리스트 반환
    @Query(value = """
        SELECT * FROM trade
        WHERE trade.status IN ('ACTIVE', 'PROCEEDING')
        AND trade_type = :tradeType
        AND (6371 * acos(cos(radians(:memberLat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:memberLon)) + sin(radians(:memberLat)) * sin(radians(latitude)))) <= 1
        ORDER BY updated_at DESC
        """, nativeQuery = true)
    List<Trade> findNearbyTrades(
            @Param("memberLat") double memberLat,
            @Param("memberLon") double memberLon,
            @Param("tradeType") String tradeType
    );

    // 사용자와의 거리가 가까운 게시글을 최신순으로 정렬하여 리스트 반환
    @Query(value = """
        SELECT * FROM trade
        WHERE trade.status IN ('ACTIVE', 'PROCEEDING')
        AND (6371 * acos(cos(radians(:memberLat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:memberLon)) + sin(radians(:memberLat)) * sin(radians(latitude)))) <= 1
        ORDER BY updated_at DESC
        """, nativeQuery = true)
    List<Trade> findRecentTrades(
            @Param("memberLat") double memberLat,
            @Param("memberLon") double memberLon
    );

    // 사용자와의 거리가 가까운 게시글을 소비기한 임박순으로 정렬하여 리스트 반환
    @Query(value = """
        SELECT t.* FROM trade t
        JOIN ingredient i ON t.ingredient_id = i.ingredient_id
        WHERE t.ingredient_id = i.ingredient_id
        AND t.status IN ('ACTIVE', 'PROCEEDING')
        AND (6371 * acos(cos(radians(:memberLat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:memberLon)) + sin(radians(:memberLat)) * sin(radians(latitude)))) <= 1
        ORDER BY i.d_day ASC
        """, nativeQuery = true)
    List<Trade> findNearExpiryTrades(
            @Param("memberLat") double memberLat,
            @Param("memberLon") double memberLon
    );

    // 사용자와의 거리가 가까운 게시글을 소비기한 여유순으로 정렬하여 리스트 반환
    @Query(value = """
        SELECT t.* FROM trade t
        JOIN ingredient i ON t.ingredient_id = i.ingredient_id
        WHERE t.ingredient_id = i.ingredient_id
        AND t.status IN ('ACTIVE', 'PROCEEDING')
        AND (6371 * acos(cos(radians(:memberLat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:memberLon)) + sin(radians(:memberLat)) * sin(radians(latitude)))) <= 1
        ORDER BY i.d_day DESC
        """, nativeQuery = true)
    List<Trade> findFarExpiryTrades(
            @Param("memberLat") double memberLat,
            @Param("memberLon") double memberLon
    );
    // 사용자의 동네 근처에서 최근 업로드된 다른 품앗이 리스트 반환
    @Query(value = """
    SELECT t.* FROM trade t
    WHERE t.member_id != :memberId
    AND t.trade_id != :tradeId
    AND t.status IN ('ACTIVE', 'PROCEEDING')
    AND (6371 * acos(cos(radians(:memberLat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:memberLon)) + sin(radians(:memberLat)) * sin(radians(latitude)))) <= 1
    ORDER BY t.updated_at DESC
    LIMIT :limit
    """, nativeQuery = true)
    List<Trade> findOtherTrades(
            @Param("memberLat") double memberLat,
            @Param("memberLon") double memberLon,
            @Param("memberId") Long memberId,
            @Param("tradeId") Long tradeId,
            @Param("limit") int limit
    );

}

