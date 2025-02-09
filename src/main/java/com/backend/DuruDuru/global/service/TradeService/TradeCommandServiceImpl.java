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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TradeCommandServiceImpl implements TradeCommandService {

    private final TradeRepository tradeRepository;
    private final TradeImageRepository tradeImageRepository;
    private final MemberRepository memberRepository;
    private final IngredientRepository ingredientRepository;
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

    private TradeImg findTradeImgById(Long tradeImgId) {
        return tradeImageRepository.findById(tradeImgId)
                .orElseThrow(() -> new IllegalArgumentException("Trade image not found. ID: " + tradeImgId));
    }

    private String getImgUrl(MultipartFile img) {
        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());

        return s3Manager.uploadFile(s3Manager.generatePostName(savedUuid), img);
    }

    // Trade 엔티티를 저장하는 메서드
    @Override
    @Transactional
    public Trade createTrade(Long memberId, Long ingredientId, TradeRequestDTO.CreateTradeRequestDTO request, List<MultipartFile> tradeImgs) {
        Member member = findMemberById(memberId);
        Ingredient ingredient = findIngredientById(ingredientId);

        if(tradeImgs.size() > 5) throw new IllegalArgumentException("게시글에 등록할 수 있는 이미지의 수는 최대 5개입니다.");
        if(!member.getIngredients().contains(ingredient)) throw new IllegalArgumentException("접근 권한이 없는 식재료입니다.");

        Trade newTrade = TradeConverter.toCreateTrade(request, member, ingredient);
        member.addTrades(newTrade);
        tradeRepository.save(newTrade);

        // 이미지 업로드 처리
        if (tradeImgs != null) {
            for (MultipartFile img : tradeImgs) {
                if (img.isEmpty()) continue;

                String tradeImgUrl = getImgUrl(img);

                TradeImg newTradeImage = TradeImageConverter.toTradeImg(tradeImgUrl, newTrade);
                tradeImageRepository.save(newTradeImage);
                newTrade.getTradeImgs().add(newTradeImage);
            }
        }

        return tradeRepository.save(newTrade);
    }

    // 품앗이 게시글 삭제
    @Override
    @Transactional
    public void deleteTrade(Long memberId, Long tradeId) {
        Trade trade = findTradeById(tradeId);
        Member member = findMemberById(memberId);

        if(!trade.getMember().getMemberId().equals(member.getMemberId())) throw new IllegalArgumentException("접근 권한이 없는 게시글입니다.");

        tradeRepository.delete(trade);
    }

    // 품앗이 게시글 수정
    @Override
    @Transactional
    public Trade updateTrade(Long memberId, Long tradeId, TradeRequestDTO.UpdateTradeRequestDTO request) {
        Trade trade = findTradeById(tradeId);
        Member member = findMemberById(memberId);

        if(!trade.getMember().getMemberId().equals(member.getMemberId())) throw new IllegalArgumentException("접근 권한이 없는 게시글입니다.");

        // 기존 이미지를 삭제한 경우
        if (request.getDeleteImgIds() != null && !request.getDeleteImgIds().isEmpty()) {
            for(Long imgId : request.getDeleteImgIds()) {
                TradeImg existingTradeImg = findTradeImgById(imgId);

                if (!trade.getTradeImgs().contains(existingTradeImg)) throw new IllegalArgumentException("해당 게시글에 포함된 이미지가 아닙니다. TradeImgId: " + imgId);

                // DB, S3에서 삭제
                trade.getTradeImgs().remove(existingTradeImg);
                tradeImageRepository.delete(existingTradeImg);
                s3Manager.deleteFile(existingTradeImg.getTradeImgUrl());
            }
        }

        // 새로운 이미지를 추가한 경우
        if (request.getAddTradeImgs() != null) {
            if(request.getAddTradeImgs().size() + trade.getTradeImgs().size() > 5) throw new IllegalArgumentException("게시글에 등록할 수 있는 이미지의 수는 최대 5개입니다.");

            for (MultipartFile img : request.getAddTradeImgs()) {
                String tradeImgUrl = getImgUrl(img);

                TradeImg newTradeImage = TradeImageConverter.toTradeImg(tradeImgUrl, trade);
                tradeImageRepository.save(newTradeImage);
                trade.getTradeImgs().add(newTradeImage);
            }
        }

        // 기타 request 처리
        if (request.getIngredientCount() != null) trade.setIngredientCount(request.getIngredientCount());
        if (request.getBody() != null) trade.setBody(request.getBody());
        if (request.getTradeType() != null) trade.setTradeType(request.getTradeType());
        if (request.getStatus() != null) trade.setStatus(request.getStatus());

        return tradeRepository.save(trade);
    }
}
