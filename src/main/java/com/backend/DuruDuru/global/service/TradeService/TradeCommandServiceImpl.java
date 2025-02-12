package com.backend.DuruDuru.global.service.TradeService;

import com.backend.DuruDuru.global.S3.AmazonS3Manager;
import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.handler.IngredientHandler;
import com.backend.DuruDuru.global.apiPayload.exception.handler.MemberException;
import com.backend.DuruDuru.global.apiPayload.exception.handler.TownHandler;
import com.backend.DuruDuru.global.apiPayload.exception.handler.TradeHandler;
import com.backend.DuruDuru.global.converter.TradeConverter;
import com.backend.DuruDuru.global.converter.TradeImageConverter;
import com.backend.DuruDuru.global.domain.entity.*;
import com.backend.DuruDuru.global.domain.enums.Status;
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
    private final TradeImageRepository tradeImageRepository;
    private final MemberRepository memberRepository;
    private final IngredientRepository ingredientRepository;
    private final LikeTradeRepository likeTradeRepository;
    private final UuidRepository uuidRepository;
    private final AmazonS3Manager s3Manager;

    // Trade 엔티티를 저장하는 메서드
    @Override
    @Transactional
    public Trade createTrade(Long memberId, Long ingredientId, TradeRequestDTO.CreateTradeRequestDTO request) {
        Member member = findMemberById(memberId);
        Ingredient ingredient = findIngredientById(ingredientId);

        // Member에 Town 정보가 없을 경우
        if(member.getTown() == null) {
            throw new TownHandler(ErrorStatus.TOWN_NOT_REGISTERED);
        }
        // 진행중인 품앗이 거래가 이미 최대 개수인 4개인 경우
        if(tradeRepository.findActiveTradesByMember(memberId).size() >= 4) {
            throw new TradeHandler(ErrorStatus.TRADE_MAX_LIMIT_REACHED);
        }
        // 접근 권한이 없는 식재료의 Id를 입력한 경우
        if(!member.getIngredients().contains(ingredient)) {
            throw new IngredientHandler(ErrorStatus.INGREDIENT_ACCESS_DENIED);
        }
        // 등록 가능한 이미지의 최대 개수를 넘은 경우
        if(request.getTradeImgs() != null && request.getTradeImgs().size() > 5) {
            throw new TradeHandler(ErrorStatus.TRADE_IMAGE_LIMIT_REACHED);
        }

        // 품앗이 등록 가능한 식재료 개수보다 요청한 개수가 많을 경우
        if (request.getIngredientCount() > getLeftCount(ingredient)) {
            throw new TradeHandler(ErrorStatus.TRADE_REGISTER_DENIED);
        }

        // Trade 엔티티 생성 및 저장
        Trade newTrade = TradeConverter.toCreateTrade(request, member, ingredient);
        member.addTrades(newTrade);
        tradeRepository.save(newTrade);

        // 이미지 업로드 처리
        if (request.getTradeImgs() != null) {
            for (MultipartFile img : request.getTradeImgs()) {
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

        if(!trade.getMember().getMemberId().equals(member.getMemberId())) {
            throw new TradeHandler(ErrorStatus.TRADE_ACCESS_DENIED);
        }

        tradeRepository.delete(trade);
    }

    // 품앗이 게시글 수정
    @Override
    @Transactional
    public Trade updateTrade(Long memberId, Long tradeId, TradeRequestDTO.UpdateTradeRequestDTO request) {
        Trade trade = findTradeById(tradeId);
        Member member = findMemberById(memberId);

        if(!trade.getMember().getMemberId().equals(member.getMemberId())) {
            throw new TradeHandler(ErrorStatus.TRADE_ACCESS_DENIED);
        }

        // 기존 이미지를 삭제한 경우
        if (request.getDeleteImgIds() != null && !request.getDeleteImgIds().isEmpty()) {
            for(Long imgId : request.getDeleteImgIds()) {
                TradeImg existingTradeImg = findTradeImgById(imgId);

                if (!trade.getTradeImgs().contains(existingTradeImg)) {
                    throw new TradeHandler(ErrorStatus.TRADE_IMAGE_INVALID);
                }

                // DB, S3에서 삭제
                trade.getTradeImgs().remove(existingTradeImg);
                tradeImageRepository.delete(existingTradeImg);
                s3Manager.deleteFile(existingTradeImg.getTradeImgUrl());
            }
        }

        // 새로운 이미지를 추가한 경우
        if (request.getAddTradeImgs() != null) {
            if(request.getAddTradeImgs().size() + trade.getTradeImgs().size() > 5) {
                throw new TradeHandler(ErrorStatus.TRADE_IMAGE_LIMIT_REACHED);
            }

            for (MultipartFile img : request.getAddTradeImgs()) {
                String tradeImgUrl = getImgUrl(img);

                TradeImg newTradeImage = TradeImageConverter.toTradeImg(tradeImgUrl, trade);
                tradeImageRepository.save(newTradeImage);
                trade.getTradeImgs().add(newTradeImage);
            }
        }

        // 식재료 개수 업데이트
        if (request.getIngredientCount() != null) {
            long totalAvailable = getLeftCount(trade.getIngredient()) + trade.getIngredientCount();

            if (request.getIngredientCount() > totalAvailable) {
                // 품앗이 등록 가능한 식재료 개수보다 요청한 개수가 많을 경우
                throw new TradeHandler(ErrorStatus.TRADE_REGISTER_DENIED);
            } else {
                trade.setIngredientCount(request.getIngredientCount());
            }
        }

        // 본문 내용 업데이트
        if (request.getBody() != null) {
            trade.setBody(request.getBody());
        }

        // 품앗이 타입 업데이트
        if (request.getTradeType() != null) {
            trade.setTradeType(request.getTradeType());
        }

        // 품앗이 상태 업데이트
        if (request.getStatus() != null) {
            Status requestStatus = request.getStatus();

            // 완료된 품앗이를 거래 전, 거래 중으로 바꾸는 경우
            if((requestStatus == Status.ACTIVE || requestStatus == Status.PROCEEDING)
                    && tradeRepository.findActiveTradesByMember(memberId).size() >= 4) {
                throw new TradeHandler(ErrorStatus.TRADE_MAX_LIMIT_REACHED);
            } else {
                trade.setStatus(requestStatus);
            }
        }

        return tradeRepository.save(trade);
    }

    // 찜하기 등록
    @Override
    @Transactional
    public LikeTrade createLike(Long memberId, Long tradeId) {
        Member member = findMemberById(memberId);
        Trade trade = findTradeById(tradeId);

        if(likeTradeRepository.findByMemberAndTrade(member, trade) != null) {
            throw new TradeHandler(ErrorStatus.TRADE_ALREADY_LIKE);
        }

        // LieTrade 엔티티 생성 및 저장
        LikeTrade likeTrade = TradeConverter.toLikeTrade(member, trade);
        member.addLikeTrades(likeTrade);
        trade.addLikeTrades(likeTrade);

        // 찜하기 수 증가
        trade.increaseLikeCount();

        return likeTradeRepository.save(likeTrade);
    }

    @Override
    @Transactional
    public void deleteLike(Long memberId, Long tradeId) {
        Member member = findMemberById(memberId);
        Trade trade = findTradeById(tradeId);

        LikeTrade likeTrade = likeTradeRepository.findByMemberAndTrade(member, trade);

        if(likeTrade == null) {
            throw new TradeHandler(ErrorStatus.TRADE_LIKE_NOT_EXIST);
        }

        trade.decreaseLikeCount();
        likeTradeRepository.delete(likeTrade);
    }


    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private Ingredient findIngredientById(Long ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IngredientHandler(ErrorStatus.INGREDIENT_NOT_FOUND));
    }

    private Trade findTradeById(Long tradeId) {
        return tradeRepository.findById(tradeId)
                .orElseThrow(() -> new TradeHandler(ErrorStatus.TRADE_NOT_FOUND));
    }

    private TradeImg findTradeImgById(Long tradeImgId) {
        return tradeImageRepository.findById(tradeImgId)
                .orElseThrow(() -> new TradeHandler(ErrorStatus.TRADE_IMAGE_NOT_FOUND));
    }

    private String getImgUrl(MultipartFile img) {
        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());

        return s3Manager.uploadFile(s3Manager.generatePostName(savedUuid), img);
    }

    // 품앗이 가능한 남은 식재료 개수 반환
    private long getLeftCount(Ingredient ingredient) {
        long tradingCount = ingredient.getTrades().stream()
                .mapToLong(Trade::getIngredientCount)
                .sum();

        return ingredient.getCount() - tradingCount;
    }
}
