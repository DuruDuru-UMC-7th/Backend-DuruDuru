package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.converter.TradeConverter;
import com.backend.DuruDuru.global.domain.entity.Ingredient;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.repository.IngredientRepository;
import com.backend.DuruDuru.global.repository.MemberRepository;
import com.backend.DuruDuru.global.repository.TradeRepository;
import com.backend.DuruDuru.global.web.dto.Trade.TradeRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeCommandServiceImpl implements TradeCommandService {

    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;
    private final IngredientRepository ingredientRepository;

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found. ID: " + memberId));
    }

    private Ingredient findIngredientById(Long ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found. ID: " + ingredientId));
    }

    private Trade findTradeById(Long tradeId) {
        return tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found. ID: " + tradeId));
    }

    // Trade 엔티티를 저장하는 메서드
    @Override
    @Transactional
    public Trade createTrade(Long memberId, Long ingredientId, TradeRequestDTO.CreateTradeRequestDTO request) {
        Member member = findMemberById(memberId);
        Ingredient ingredient = findIngredientById(ingredientId);

        Trade newTrade = TradeConverter.toCreateTrade(request, member, ingredient);
        member.addTrades(newTrade);
        // memberRepository.save(member);
        return tradeRepository.save(newTrade);
    }

    // 품앗이 게시글 상세 조회
    @Override
    @Transactional
    public Trade getTrade(Long tradeId) {
        return findTradeById(tradeId);
    }

    @Override
    @Transactional
    public Trade updateTrade(Long memberId, Long tradeId, TradeRequestDTO.UpdateTradeRequestDTO request) {
        Trade updateTrade = findTradeById(tradeId);

        return tradeRepository.save(updateTrade);
    }
}
