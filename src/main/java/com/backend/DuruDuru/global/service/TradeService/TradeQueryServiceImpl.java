package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.handler.MemberException;
import com.backend.DuruDuru.global.apiPayload.exception.handler.TradeHandler;
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

    // 품앗이 게시글 상세 조회
    @Override
    @Transactional
    public Trade getTrade(Long tradeId) {
        return findTradeById(tradeId);
    }

    // 근처 품앗이 TradeType별로 분류된 게시글 리스트 반환
    @Override
    @Transactional
    public List<Trade> getNearTradesByType(Long memberId, TradeType tradeType) {
        Member member = findMemberById(memberId);
        return tradeRepository.findNearbyTrades(member.getTown().getLatitude(), member.getTown().getLongitude(), tradeType.name());
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

    // 근처 품앗이 소비기한 임박순 조회
    @Override
    @Transactional
    public List<Trade> getNearExpiryTrade(Long memberId) {
        Member member = findMemberById(memberId);
        return tradeRepository.findNearExpiryTrades(member.getTown().getLatitude(), member.getTown().getLongitude());
    }

    // 근처 품앗이 소비기한 여유순 조회
    @Override
    @Transactional
    public List<Trade> getFarExpiryTrade(Long memberId) {
        Member member = findMemberById(memberId);
        return tradeRepository.findFarExpiryTrades(member.getTown().getLatitude(), member.getTown().getLongitude());
    }

    private Trade findTradeById(Long tradeId) {
        return tradeRepository.findById(tradeId)
                .orElseThrow(() -> new TradeHandler(ErrorStatus.TRADE_NOT_FOUND));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }
}
