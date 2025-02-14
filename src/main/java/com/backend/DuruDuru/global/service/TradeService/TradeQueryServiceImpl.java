package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.AuthException;
import com.backend.DuruDuru.global.apiPayload.exception.handler.TradeHandler;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import com.backend.DuruDuru.global.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeQueryServiceImpl implements TradeQueryService {

    private final TradeRepository tradeRepository;

    // 품앗이 게시글 상세 조회
    @Override
    @Transactional
    public Trade getTrade(Member member, Long tradeId) {
        validateLoggedInMember(member);
        return findTradeById(tradeId);
    }

    // 근처 품앗이 TradeType별로 분류된 게시글 리스트 반환
    @Override
    @Transactional
    public List<Trade> getNearTradesByType(Member member, TradeType tradeType) {
        validateLoggedInMember(member);
        return tradeRepository.findNearbyTrades(member.getTown().getLatitude(), member.getTown().getLongitude(), tradeType.name());
    }

    // 멤버별 전체 품앗이 게시글 리스트 조회
    @Override
    @Transactional
    public List<Trade> getAllTradesByMember(Member member) {
        validateLoggedInMember(member);
        return tradeRepository.findAllByMemberOrderByUpdatedAtDesc(member);
    }

    // 멤버별 활성화 품앗이 게시글 리스트 조회
    @Override
    @Transactional
    public List<Trade> getActiveTradesByMember(Member member) {
        validateLoggedInMember(member);
        return tradeRepository.findActiveTradesByMember(member.getMemberId());
    }

    // 근처 품앗이 최신 등록순 조회
    @Override
    @Transactional
    public List<Trade> getRecentTrades(Member member) {
        validateLoggedInMember(member);
        return tradeRepository.findRecentTrades(member.getTown().getLatitude(), member.getTown().getLongitude());
    }

    // 근처 품앗이 소비기한 임박순 조회
    @Override
    @Transactional
    public List<Trade> getNearExpiryTrade(Member member) {
        validateLoggedInMember(member);
        return tradeRepository.findNearExpiryTrades(member.getTown().getLatitude(), member.getTown().getLongitude());
    }

    // 근처 품앗이 소비기한 여유순 조회
    @Override
    @Transactional
    public List<Trade> getFarExpiryTrade(Member member) {
        validateLoggedInMember(member);
        return tradeRepository.findFarExpiryTrades(member.getTown().getLatitude(), member.getTown().getLongitude());
    }

    // 다른 품앗이 조회
    @Override
    @Transactional
    public List<Trade> getOtherTrade(Member member, Long tradeId) {
        validateLoggedInMember(member);
        Trade trade = findTradeById(tradeId);
        return tradeRepository.findOtherTrades(member.getTown().getLatitude(), member.getTown().getLongitude(), member.getMemberId(), tradeId, 4);
    }

    @Override
    @Transactional
    public List<Trade> getAllLikeTradesByMember(Member member) {
        validateLoggedInMember(member);
        return tradeRepository.findLikeTradesByMember(member.getMemberId());
    }

    // 로그인 여부 확인
    private void validateLoggedInMember(Member member) {
        if (member == null) {
            throw new AuthException(ErrorStatus.LOGIN_REQUIRED);
        }
    }

    private Trade findTradeById(Long tradeId) {
        return tradeRepository.findById(tradeId)
                .orElseThrow(() -> new TradeHandler(ErrorStatus.TRADE_NOT_FOUND));
    }

}
