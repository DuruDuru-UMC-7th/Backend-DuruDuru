package com.backend.DuruDuru.global.web.controller;


import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.converter.TradeConverter;
import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.enums.Status;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import com.backend.DuruDuru.global.service.TradeService.TradeCommandService;
import com.backend.DuruDuru.global.service.TradeService.TradeQueryService;
import com.backend.DuruDuru.global.web.dto.Trade.TradeRequestDTO;
import com.backend.DuruDuru.global.web.dto.Trade.TradeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
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
            @RequestParam Long memberId,
            @RequestParam Long ingredientId,
            @RequestParam(value = "image", required = false) List<MultipartFile> tradeImgs, // 이미지 리스트 처리
            @RequestParam TradeType tradeType,
            @RequestParam String body,
            @RequestParam Long ingredientCount
    ){
        TradeRequestDTO.CreateTradeRequestDTO request = new TradeRequestDTO.CreateTradeRequestDTO(ingredientCount, body, tradeType, tradeImgs);
        Trade trade = tradeCommandService.createTrade(memberId, ingredientId, request, tradeImgs);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, TradeConverter.toTradeDetailDTO(trade));
    }

    // 품앗이 상세 조회
    @GetMapping("/{trade_id}")
    @Operation(summary = "품앗이 게시글 상세 조회 API", description = "특정 품앗이 게시글을 상세 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradeDetailResultDTO> findTradeById(
            @PathVariable("trade_id") Long tradeId
    ){
        Trade trade = tradeQueryService.getTrade(tradeId);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, TradeConverter.toTradeDetailDTO(trade));
    }

    // 품앗이 게시글 삭제
    @DeleteMapping("/{trade_id}")
    @Operation(summary = "품앗이 게시글 삭제 API", description = "특정 품앗이를 삭제하는 API 입니다.")
    public ApiResponse<?> deleteTrade(
            @RequestParam Long memberId,
            @PathVariable("trade_id") Long tradeId
    ){
        tradeCommandService.deleteTrade(memberId, tradeId);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 품앗이 게시글 수정
    @PatchMapping(value = "/{trade_id}", consumes = "multipart/form-data")
    @Operation(summary = "품앗이 게시글 수정 API", description = "특정 품앗이를 수정하는 API 입니다. 삭제할 이미지는 해당 이미지의 id값으로 넣어주세요.")
    public ApiResponse<TradeResponseDTO.TradeDetailResultDTO> updateTrade(
            @PathVariable("trade_id") Long tradeId,
            @RequestParam Long memberId,
            @RequestParam(required = false) List<Long> deleteImgIds,
            @RequestParam(value = "addImgs", required = false) List<MultipartFile> addImgs, // 이미지 리스트 처리
            @RequestParam(required = false) TradeType tradeType,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String body,
            @RequestParam(required = false) Long ingredientCount
    ){
        TradeRequestDTO.UpdateTradeRequestDTO request = new TradeRequestDTO.UpdateTradeRequestDTO(ingredientCount, body, tradeType, status, deleteImgIds, addImgs);
        Trade trade = tradeCommandService.updateTrade(memberId, tradeId, request);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, TradeConverter.toTradeDetailDTO(trade));
    }

    // 나의 활성화 품앗이 리스트 조회
    @GetMapping("/my/active")
    @Operation(summary = "나의 활성화 품앗이 리스트 조회 API", description = "나의 활성화된 품앗이 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradePreviewListDTO> findCurrentTrade(
            @RequestParam Long memberId
    ){
        List<Trade> tradeList = tradeQueryService.getActiveTradesByMember(memberId);
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, TradeConverter.toTradePreviewListDTO(tradeList));
    }

    // 나의 전체 품앗이 리스트 조회
    @GetMapping("/my/history")
    @Operation(summary = "나의 전체 품앗이 리스트 조회 API", description = "나의 전체 품앗이 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradePreviewListDTO> findHistoryTrade(
            @RequestParam Long memberId
    ){
        List<Trade> tradeList = tradeQueryService.getAllTradesByMember(memberId);
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, TradeConverter.toTradePreviewListDTO(tradeList));
    }

    // 내가 찜한 품앗이 리스트 조회
    @GetMapping("/my/like")
    @Operation(summary = "내가 찜한 품앗이 리스트 조회 API", description = "내가 찜한 품앗이 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<?> findLikeTrade(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 내 근처 품앗이 나눔/교환별 리스트 조회
    @GetMapping("/near/{trade_type}")
    @Operation(summary = "내 근처 품앗이 나눔/교환별 리스트 조회 API", description = "내 근처 게시글 중에서 나눔/교환별로 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradePreviewListDTO> findNearTradeByType(
            @RequestParam Long memberId,
            @PathVariable("trade_type") TradeType tradeType
    ){
        List<Trade> tradeList = tradeQueryService.getNearTradesByType(memberId, tradeType);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, TradeConverter.toTradePreviewListDTO(tradeList));
    }

    // 내 근처 품앗이 최신 등록순 리스트 조회
    @GetMapping("/near/recent")
    @Operation(summary = "내 근처 품앗이 최신 등록순 리스트 조회 API", description = "내 근처 게시글 중에서 최신 등록순으로 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.TradePreviewListDTO> findRecentTrade(
            @RequestParam Long memberId
    ){
        List<Trade> tradeList = tradeQueryService.getRecentTrades(memberId);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, TradeConverter.toTradePreviewListDTO(tradeList));
    }

    // 내 근처 품앗이 소비기한 임박순 리스트 조회
    @GetMapping("/near/near-expiry")
    @Operation(summary = "내 근처 품앗이 소비기한 임박순 리스트 조회 API", description = "내 근처 게시글 중에서 소비기한 임박순으로 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<?> findNearExpiryTrade(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 내 근처 품앗이 소비기한 여유순 리스트 조회
    @GetMapping("/near/far-expiry")
    @Operation(summary = "내 근처 품앗이 소비기한 여유순 리스트 조회 API", description = "내 근처 게시글 중에서 소비기한 여유순으로 **프리뷰 리스트**를 조회하는 API 입니다.")
    public ApiResponse<?> findFarExpiryTrade(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 다른 품앗이 둘러보기
    @GetMapping("/other-trade")
    @Operation(summary = "다른 품앗이 둘러보기 API", description = "다른 품앗이를 추천하는 API 입니다.")
    public ApiResponse<?> findOtherTrade(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 품앗이 찜하기
    @PostMapping("/like/{member_id}/{trade_id}")
    @Operation(summary = "품앗이 찜하기 API", description = "품앗이 찜하기 API 입니다.")
    public ApiResponse<?> likeTrade() {
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 품앗이 찜하기 취소
    @DeleteMapping("/like/delete/{trade_id}")
    @Operation(summary = "품앗이 찜하기 취소 API", description = "품앗이 찜하기를 취소하는 API 입니다.")
    public ApiResponse<?> deleteLideTrade(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 특정 품앗이의 찜하기 개수 조회
    @GetMapping("/like/count/{trade_id}")
    @Operation(summary = "특정 품앗이의 찜하기 개수 조회 API", description = "특정 품앗이의 찜하기 개수를 조회하는 API 입니다.")
    public ApiResponse<?> findLikeCount(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 품앗이 링크 공유
    @PostMapping("/shareUrl")
    @Operation(summary = "품앗이 링크 공유 API", description = "품앗이 링크를 공유하는 API 입니다.")
    public ApiResponse<?> shareUrl(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 오늘 업로드된 품앗이 추천
    @GetMapping("/recommend/today")
    @Operation(summary = "오늘 업로드된 품앗이 추천 API", description = "오늘 업로드된 품앗이를 추천하여 목록을 조회하는 API 입니다.")
    public ApiResponse<?> RecommendTodayTrade(){

        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 품앗이 키워드 알림
    @GetMapping("/keyword/alert")
    @Operation(summary = "품앗이 키워드 알림 API", description = "품앗이 키워드 알림 API 입니다.")
    public ApiResponse<?> tradeAlarm(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }
}
