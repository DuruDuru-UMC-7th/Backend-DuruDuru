package com.backend.DuruDuru.global.web.controller;


import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.converter.TradeConverter;
import com.backend.DuruDuru.global.domain.entity.LikeTrade;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.enums.Status;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import com.backend.DuruDuru.global.security.handler.annotation.AuthUser;
import com.backend.DuruDuru.global.service.TradeService.TradeCommandService;
import com.backend.DuruDuru.global.service.TradeService.TradeQueryService;
import com.backend.DuruDuru.global.web.dto.Trade.TradeRequestDTO;
import com.backend.DuruDuru.global.web.dto.Trade.TradeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/trade")
@Tag(name = "품앗이 API", description = "품앗이 API입니다.")
public class TradeController {

    private final TradeCommandService tradeCommandService;
    private final TradeQueryService tradeQueryService;

    // 품앗이 게시글 등록
    @PostMapping(value = "/", consumes = "multipart/form-data")
    @Operation(summary = "품앗이 게시글 등록 API", description = "품앗이 게시글을 등록하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradeDetailResultDTO> createTrade(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @RequestParam Long ingredientId,
            @ModelAttribute TradeRequestDTO.CreateTradeRequestDTO request
    ){
        Trade trade = tradeCommandService.createTrade(member, ingredientId, request);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_CREATE_OK, TradeConverter.toTradeDetailDTO(trade));
    }

    // 품앗이 상세 조회
    @GetMapping("/{trade_id}")
    @Operation(summary = "품앗이 게시글 상세 조회 API", description = "특정 품앗이 게시글을 상세 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradeDetailResultDTO> findTradeById(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @PathVariable("trade_id") Long tradeId
    ){
        Trade trade = tradeQueryService.getTrade(member, tradeId);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_GET_DETAIL_OK, TradeConverter.toTradeDetailDTO(trade));
    }

    // 품앗이 게시글 삭제
    @DeleteMapping("/{trade_id}")
    @Operation(summary = "품앗이 게시글 삭제 API", description = "특정 품앗이를 삭제하는 API 입니다.")
    public ApiResponse<?> deleteTrade(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @PathVariable("trade_id") Long tradeId
    ){
        tradeCommandService.deleteTrade(member, tradeId);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_DELETE_OK, null);
    }

    // 품앗이 게시글 수정
    @PatchMapping(value = "/{trade_id}", consumes = "multipart/form-data")
    @Operation(summary = "품앗이 게시글 수정 API", description = "특정 품앗이를 수정하는 API 입니다. 삭제할 이미지는 해당 이미지의 id값으로 넣어주세요.")
    public ApiResponse<TradeResponseDTO.TradeDetailResultDTO> updateTrade(
            @PathVariable("trade_id") Long tradeId,
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @RequestParam(required = false) List<Long> deleteImgIds,
            @RequestParam(value = "addImgs", required = false) List<MultipartFile> addImgs, // 이미지 리스트 처리
            @RequestParam(required = false) TradeType tradeType,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String body,
            @RequestParam(required = false) Long ingredientCount
    ){
        TradeRequestDTO.UpdateTradeRequestDTO request = new TradeRequestDTO.UpdateTradeRequestDTO(ingredientCount, body, tradeType, status, deleteImgIds, addImgs);
        Trade trade = tradeCommandService.updateTrade(member, tradeId, request);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_UPDATE_OK, TradeConverter.toTradeDetailDTO(trade));
    }

    // 나의 활성화 품앗이 리스트 조회
    @GetMapping("/my/active")
    @Operation(summary = "나의 활성화 품앗이 리스트 조회 API", description = "나의 활성화된 품앗이 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradePreviewListDTO> findCurrentTrade(
            @Parameter(name = "user", hidden = true) @AuthUser Member member
    ){
        List<Trade> tradeList = tradeQueryService.getActiveTradesByMember(member);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_GET_LIST_OK, TradeConverter.toTradePreviewListDTO(tradeList));
    }

    // 나의 전체 품앗이 리스트 조회
    @GetMapping("/my/history")
    @Operation(summary = "나의 전체 품앗이 리스트 조회 API", description = "나의 전체 품앗이 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradePreviewListDTO> findHistoryTrade(
            @Parameter(name = "user", hidden = true) @AuthUser Member member
    ){
        List<Trade> tradeList = tradeQueryService.getAllTradesByMember(member);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_GET_LIST_OK, TradeConverter.toTradePreviewListDTO(tradeList));
    }

    // 내가 찜한 품앗이 리스트 조회
    @GetMapping("/my/like")
    @Operation(summary = "내가 찜한 품앗이 리스트 조회 API", description = "내가 찜한 품앗이 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradePreviewListDTO> findLikeTrade(
            @Parameter(name = "user", hidden = true) @AuthUser Member member
    ){
        List<Trade> tradeList = tradeQueryService.getAllLikeTradesByMember(member);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_GET_LIST_OK, TradeConverter.toTradePreviewListDTO(tradeList));
    }

    // 내 근처 품앗이 나눔/교환별 리스트 조회
    @GetMapping("/near/{trade_type}")
    @Operation(summary = "내 근처 품앗이 나눔/교환별 리스트 조회 API", description = "내 근처 게시글 중에서 나눔/교환별로 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradePreviewListDTO> findNearTradeByType(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @PathVariable("trade_type") TradeType tradeType
    ){
        List<Trade> tradeList = tradeQueryService.getNearTradesByType(member, tradeType);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_GET_LIST_OK, TradeConverter.toTradePreviewListDTO(tradeList));
    }

    // 내 근처 품앗이 최신 등록순 리스트 조회
    @GetMapping("/near/recent")
    @Operation(summary = "내 근처 품앗이 최신 등록순 리스트 조회 API", description = "내 근처 게시글 중에서 최신 등록순으로 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradePreviewListDTO> findRecentTrade(
            @Parameter(name = "user", hidden = true) @AuthUser Member member
    ){
        List<Trade> tradeList = tradeQueryService.getRecentTrades(member);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_GET_LIST_OK, TradeConverter.toTradePreviewListDTO(tradeList));
    }

    // 내 근처 품앗이 소비기한 임박순 리스트 조회
    @GetMapping("/near/near-expiry")
    @Operation(summary = "내 근처 품앗이 소비기한 임박순 리스트 조회 API", description = "내 근처 게시글 중에서 소비기한 임박순으로 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradePreviewListDTO> findNearExpiryTrade(
            @Parameter(name = "user", hidden = true) @AuthUser Member member
    ) {
        List<Trade> tradeList = tradeQueryService.getNearExpiryTrade(member);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_GET_LIST_OK, TradeConverter.toTradePreviewListDTO(tradeList));
    }

    // 내 근처 품앗이 소비기한 여유순 리스트 조회
    @GetMapping("/near/far-expiry")
    @Operation(summary = "내 근처 품앗이 소비기한 여유순 리스트 조회 API", description = "내 근처 게시글 중에서 소비기한 여유순으로 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradePreviewListDTO> findFarExpiryTrade(
            @Parameter(name = "user", hidden = true) @AuthUser Member member
    ) {
        List<Trade> tradeList = tradeQueryService.getFarExpiryTrade(member);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_GET_LIST_OK, TradeConverter.toTradePreviewListDTO(tradeList));
    }

    // 다른 품앗이 둘러보기
    @GetMapping("/other-trade")
    @Operation(summary = "다른 품앗이 둘러보기 API", description = "사용자의 동네 근처에서 최근 업로드된 다른 품앗이를 추천하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradePreviewListDTO> findOtherTrade(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @RequestParam Long tradeId
    ){
        List<Trade> tradeList = tradeQueryService.getOtherTrade(member, tradeId);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_GET_LIST_OK, TradeConverter.toTradePreviewListDTO(tradeList));
    }

    // 품앗이 찜하기
    @PostMapping("/like/{trade_id}")
    @Operation(summary = "품앗이 찜하기 API", description = "품앗이 찜하기 API 입니다.")
    public ApiResponse<TradeResponseDTO.LikeTradeResultDTO> likeTrade(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @PathVariable("trade_id") Long tradeId

    ) {
        LikeTrade likeTrade = tradeCommandService.createLike(member, tradeId);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_LIKE_OK, TradeConverter.toLikeTradeResultDTO(likeTrade));
    }

    // 품앗이 찜하기 취소
    @DeleteMapping("/like/{trade_id}/delete")
    @Operation(summary = "품앗이 찜하기 취소 API", description = "품앗이 찜하기를 취소하는 API 입니다.")
    public ApiResponse<?> deleteLideTrade(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @PathVariable("trade_id") Long tradeId
    ){
        tradeCommandService.deleteLike(member, tradeId);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_LIKE_DELETE_OK, null);
    }

    // 특정 품앗이의 찜하기 개수 조회
    @GetMapping("/like/{trade_id}/count")
    @Operation(summary = "특정 품앗이의 찜하기 개수 조회 API", description = "특정 품앗이의 찜하기 개수를 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.LikeCountResultDTO> findLikeCount(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @PathVariable("trade_id") Long tradeId
    ){
        Trade trade = tradeQueryService.getTrade(member, tradeId);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_LIKE_COUNT_OK, TradeConverter.toLikeCountResultDTO(trade));
    }

    // 품앗이 키워드 알림
    @GetMapping("/keyword/alert")
    @Operation(summary = "품앗이 키워드 알림 API", description = "품앗이 키워드 알림 API 입니다.")
    public ApiResponse<?> tradeAlarm(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }
}
