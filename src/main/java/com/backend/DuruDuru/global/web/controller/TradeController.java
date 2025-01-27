package com.backend.DuruDuru.global.web.controller;


import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.converter.TradeConverter;
import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.service.TradeService.TradeCommandService;
import com.backend.DuruDuru.global.web.dto.Trade.TradeRequestDTO;
import com.backend.DuruDuru.global.web.dto.Trade.TradeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/trade")
@Tag(name = "품앗이 API", description = "품앗이 API입니다.")
public class TradeController {

    private final TradeCommandService tradeCommandService;

    // 품앗이 게시글 등록
    @PostMapping("/")
    @Operation(summary = "품앗이 게시글 등록 API", description = "품앗이 게시글을 등록하는 API 입니다.")
    public ApiResponse<TradeResponseDTO.CreateTradeResultDTO> createTrade(
            @RequestParam Long memberId, Long ingredientId,
            @RequestBody TradeRequestDTO.CreateTradeRequestDTO request
            ){
        Trade trade = tradeCommandService.createTrade(memberId, ingredientId, request);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, TradeConverter.toCreateTradeResultDTO(trade));
    }

    // 품앗이 게시글 삭제
    @DeleteMapping("/{trade_id}")
    @Operation(summary = "품앗이 게시글 삭제 API", description = "특정 품앗이를 삭제하는 API 입니다.")
    public ApiResponse<?> deleteTradeById(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 품앗이 게시글 수정
    @PatchMapping("/{trade_id}")
    @Operation(summary = "품앗이 게시글 수정 API", description = "특정 품앗이를 수정하는 API 입니다.")
    public ApiResponse<?> updateTradeById(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 품앗이 상세 조회
    @GetMapping("/{trade_id}")
    @Operation(summary = "품앗이 게시글 상세 조회 API", description = "특정 품앗이 게시글을 상세 조회하는 API 입니다.")
    public ApiResponse<?> findTradeById(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 품앗이 가능한 식재료 조회
    @GetMapping("/ingredient/available")
    @Operation(summary = "품앗이 가능한 식재료 조회 API", description = "품앗이 가능한 식재료 목록을 조회하는 API 입니다.")
    public ApiResponse<?> findAvailableIngredients(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 내 근처 품앗이 나눔/교환별 조회
    @GetMapping("/near")
    @Operation(summary = "내 근처 품앗이 나눔/교환별 조회 API", description = "나눔/교환별로 내 근처 품앗이 목록을 조회하는 API 입니다.")
    public ApiResponse<?> findNearTradeByType(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 나의 품앗이 목록 조회 API
    @GetMapping("/current")
    @Operation(summary = "나의 품앗이 목록 조회 API", description = "나의 품앗이 목록을 조회하는 API 입니다.")
    public ApiResponse<?> findCurrentTrade(){
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, null);
    }

    // 나의 품앗이 기록 조회 API
    @GetMapping("/history")
    @Operation(summary = "나의 품앗이 기록 조회 API", description = "나의 품앗이 기록을 조회하는 API 입니다.")
    public ApiResponse<?> findHistoryTrade(){
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, null);
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
