package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.converter.TownConverter;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Town;
import com.backend.DuruDuru.global.security.handler.annotation.AuthUser;
import com.backend.DuruDuru.global.service.TownService.TownCommandService;
import com.backend.DuruDuru.global.service.TownService.TownQueryService;
import com.backend.DuruDuru.global.web.dto.Town.TownRequestDTO;
import com.backend.DuruDuru.global.web.dto.Town.TownResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    private final TownQueryService townQueryService;

    // 내 동네 등록 API
    @PostMapping("/")
    @Operation(summary = "내 동네 등록 API", description = "내 동네를 등록하는 API 입니다.")
    public ApiResponse<TownResponseDTO.TownResultDTO> createTown(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @RequestBody TownRequestDTO.ToTownRequestDTO request
    ){
        Town town = townCommandService.createTown(member, request);
        return ApiResponse.onSuccess(SuccessStatus.TOWN_CREATE_OK, TownConverter.toTownResponseDTO(town));
    }

    // 내 동네 조회 API
    @GetMapping("/")
    @Operation(summary = "내 동네 조회 API", description = "내 동네를 조회하는 API 입니다.")
    public ApiResponse<TownResponseDTO.TownResultDTO> findTown(
            @Parameter(name = "user", hidden = true) @AuthUser Member member
    ){
        Town town = townQueryService.findTownByMember(member);
        return ApiResponse.onSuccess(SuccessStatus.TOWN_GET_DETAIL_OK, TownConverter.toTownResponseDTO(town));
    }

    // 내 동네 수정 API
    @PatchMapping("/")
    @Operation(summary = "내 동네 수정 API", description = "내 동네를 수정하는 API 입니다.")
    public ApiResponse<TownResponseDTO.TownResultDTO> updateTown(
            @Parameter(name = "user", hidden = true) @AuthUser Member member,
            @RequestBody TownRequestDTO.ToTownRequestDTO request
    ){
        Town town = townCommandService.updateTown(member, request);
        return ApiResponse.onSuccess(SuccessStatus.TOWN_UPDATE_OK, TownConverter.toTownResponseDTO(town));
    }
}
