package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.S3.AmazonS3Manager;
import com.backend.DuruDuru.global.converter.TradeConverter;
import com.backend.DuruDuru.global.converter.TradeImageConverter;
import com.backend.DuruDuru.global.domain.entity.*;
import com.backend.DuruDuru.global.repository.*;
import com.backend.DuruDuru.global.web.dto.Trade.TradeRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TradeCommandServiceImpl implements TradeCommandService {

    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;
    private final IngredientRepository ingredientRepository;
    private final TradeImageRepository tradeImageRepository;
    private final UuidRepository uuidRepository;
    private final AmazonS3Manager s3Manager;

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
    public Trade createTrade(Long memberId, Long ingredientId, TradeRequestDTO.CreateTradeRequestDTO request, List<MultipartFile> tradeImgs) {
        Member member = findMemberById(memberId);
        Ingredient ingredient = findIngredientById(ingredientId);

        if(!member.getIngredients().contains(ingredient)) {
            throw new IllegalArgumentException("사용자가 접근할 수 있는 식재료가 아닙니다.");
        }

        Trade newTrade = TradeConverter.toCreateTrade(request, member, ingredient);
        member.addTrades(newTrade);
        tradeRepository.save(newTrade);

        // 이미지 업로드 처리
        if (tradeImgs != null) {
            for (MultipartFile img : tradeImgs) {
                if (img.isEmpty()) {
                    continue;
                }
                String uuid = UUID.randomUUID().toString();
                Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());
                String tradeImgUrl = s3Manager.uploadFile(s3Manager.generatePostName(savedUuid), img);

                TradeImg newTradeImage = TradeImageConverter.toTradeImg(tradeImgUrl, newTrade);
                tradeImageRepository.save(newTradeImage);

                System.out.println("이미지 링크 받아오기 성공" + newTradeImage.getTradeImgUrl());
                newTrade.getTradeImgs().add(newTradeImage);
            }
        }
        return tradeRepository.save(newTrade);
    }

    // 품앗이 게시글 삭제
    @Override
    public void deleteTrade(Long memberId, Long tradeId) {
        Trade trade = findTradeById(tradeId);
        Member member = findMemberById(memberId);

        if(!trade.getMember().getMemberId().equals(member.getMemberId())) {
            throw new IllegalArgumentException("접근 권한이 없는 게시글입니다.");
        }

        tradeRepository.delete(trade);
    }

    @Override
    @Transactional
    public Trade updateTrade(Long memberId, Long tradeId, TradeRequestDTO.UpdateTradeRequestDTO request) {
        Trade updateTrade = findTradeById(tradeId);

        return tradeRepository.save(updateTrade);
    }
}
