package com.backend.DuruDuru.global.web.controller;


import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
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
@RequestMapping("/member")
@Tag(name = "멤버 API", description = "멤버 API입니다.")
public class MemberController {

    // 내 동네 등록 API
    @PostMapping("/town")
    @Operation(summary = "내 동네 등록 API", description = "내 동네를 등록하는 API 입니다.")
    public ApiResponse<?> createTown(){
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, null);
    }

    // 내 동네 조회 API
    @GetMapping("/town")
    @Operation(summary = "내 동네 조회 API", description = "내 동네를 조회하는 API 입니다.")
    public ApiResponse<?> findTown(){
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, null);
    }

    // 내 동네 등록 API
    @PatchMapping("/town")
    @Operation(summary = "내 동네 등록 API", description = "내 동네를 수정하는 API 입니다.")
    public ApiResponse<?> updateTown(){
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, null);
    }

    // 나의 품앗이 목록 조회 API
    @GetMapping("/trade/current")
    @Operation(summary = "나의 품앗이 목록 조회 API", description = "나의 품앗이 목록을 조회하는 API 입니다.")
    public ApiResponse<?> findCurrentTrade(){
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, null);
    }

    // 나의 품앗이 기록 조회 API
    @GetMapping("/trade/history")
    @Operation(summary = "나의 품앗이 기록 조회 API", description = "나의 품앗이 기록을 조회하는 API 입니다.")
    public ApiResponse<?> findHistoryTrade(){
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_OK, null);
    }
}
