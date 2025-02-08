package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.service.ChattingService.ChattingQueryService;
import com.backend.DuruDuru.global.service.ChattingService.ChattingQueryServiceImpl;
import com.backend.DuruDuru.global.web.dto.Chatting.ChattingResponseDTO;
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
@RequestMapping("/chat")
@Tag(name = "채팅 API", description = "채팅 관련 API입니다.")
public class ChatController {

    private final ChattingQueryServiceImpl chattingQueryServiceImpl;

    // 채팅방 목록 조회
    @GetMapping("/rooms")
    @Operation(summary = "채팅방 목록 조회 API", description = "사용자의 채팅방 목록 조회API")
    public ApiResponse<ChattingResponseDTO.ChattingRoomListDTO> getChattingRoomList(@RequestParam Long memberId) {
        ChattingResponseDTO.ChattingRoomListDTO resultDTO = chattingQueryServiceImpl.getChattingRoomList(memberId);
        return ApiResponse.onSuccess(SuccessStatus.CHAT_OK, resultDTO);
    }

    // 채팅 내역 조회
    @GetMapping("/rooms/{roomId}/messages")
    @Operation(summary = "채팅 내역 조회 API", description = "특정 채팅방 채팅 내역을 조회 API")
    public ApiResponse<?> getChatMessages(@PathVariable Long roomId) {
        return ApiResponse.onSuccess(SuccessStatus.CHAT_OK, null);
    }

    // 채팅방 삭제
    @DeleteMapping("/rooms/{roomId}")
    @Operation(summary = "채팅방 삭제 API", description = "특정 채팅방 삭제 API")
    public ApiResponse<?> deleteChatRoom(@PathVariable Long roomId) {
        return ApiResponse.onSuccess(SuccessStatus.CHAT_OK, null);
    }

}