package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.converter.TownConverter;
import com.backend.DuruDuru.global.domain.entity.Town;
import com.backend.DuruDuru.global.service.TownService.TownCommandService;
import com.backend.DuruDuru.global.web.dto.Town.TownRequestDTO;
import com.backend.DuruDuru.global.web.dto.Town.TownResponseDTO;
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
@RequestMapping("/town")
@Tag(name = "Town API", description = "동네 설정 관련 API입니다.")
public class TownController {

    private final TownCommandService townCommandService;

    // 내 동네 등록 API
    @PostMapping("/")
    @Operation(summary = "내 동네 등록 API", description = "내 동네를 등록하는 API 입니다.")
    public ApiResponse<TownResponseDTO.TownResultDTO> createTown(
            @RequestParam Long memberId,
            @RequestBody TownRequestDTO.CreateTownRequestDTO request) {
        Town town = townCommandService.createTown(memberId, request);
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, TownConverter.toTownResponseDTO(town));
    }

    // 내 동네 조회 API
    @GetMapping("/")
    @Operation(summary = "내 동네 조회 API", description = "내 동네를 조회하는 API 입니다.")
    public ApiResponse<?> findTown(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }

    // 내 동네 수정 API
    @PatchMapping("/")
    @Operation(summary = "내 동네 수정 API", description = "내 동네를 수정하는 API 입니다.")
    public ApiResponse<?> updateTown(){
        return ApiResponse.onSuccess(SuccessStatus.TRADE_OK, null);
    }
}
