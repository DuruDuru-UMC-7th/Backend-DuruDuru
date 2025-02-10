package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import com.backend.DuruDuru.global.repository.MemberRepository;
import com.backend.DuruDuru.global.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeQueryServiceImpl implements TradeQueryService {

    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;

    private Trade findTradeById(Long tradeId) {
        return tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found. ID: " + tradeId));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found. ID: " + memberId));
    }

    // 품앗이 게시글 상세 조회
    @Override
    @Transactional
    public Trade getTrade(Long tradeId) {
        return findTradeById(tradeId);
    }

    // 가까운 거리의 게시글 중에서 TradeType별로 분류된 게시글 리스트 반환
    @Override
    @Transactional
    public List<Trade> getNearTradesByType(Long memberId, TradeType tradeType) {
        Member member = findMemberById(memberId);
        double memberLat = member.getTown().getLatitude();
        double memberLon = member.getTown().getLongitude();

        return tradeRepository.findNearbyTrades(memberLat, memberLon, tradeType.name());
    }

    // 멤버별 전체 품앗이 게시글 리스트 조회
    @Override
    @Transactional
    public List<Trade> getAllTradesByMember(Long memberId) {
        Member member = findMemberById(memberId);
        return tradeRepository.findAllByMemberOrderByUpdatedAtDesc(member);
    }

    // 멤버별 활성화 품앗이 게시글 리스트 조회
    @Override
    @Transactional
    public List<Trade> getActiveTradesByMember(Long memberId) {
        return tradeRepository.findActiveTradesByMember(memberId);
    }

    // 근처 품앗이 최신 등록순 조회
    @Override
    @Transactional
    public List<Trade> getRecentTrades(Long memberId) {
        Member member = findMemberById(memberId);
        return tradeRepository.findRecentTrades(member.getTown().getLatitude(), member.getTown().getLongitude());
    }
}
