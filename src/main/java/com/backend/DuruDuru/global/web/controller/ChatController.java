package com.backend.DuruDuru.global.web.controller;

import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.service.ChattingService.ChattingQueryServiceImpl;
import com.backend.DuruDuru.global.web.dto.Chatting.ChattingRequestDTO;
import com.backend.DuruDuru.global.web.dto.Chatting.ChattingResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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

    @PostMapping
    @Operation(summary = "채팅방 만들기 API", description = "채팅방을 만드는 API")
    public ApiResponse<ChattingResponseDTO.ChattingRoomMakeResponseDTO> createChattingRoom(
            // 실제 구현에서는 @AuthUser를 통해 로그인한 Member 객체를 주입받습니다.
            @RequestParam Long myId,
            @RequestBody ChattingRequestDTO.ChattingRoomMakeRequestDTO requestDTO) {
        ChattingResponseDTO.ChattingRoomMakeResponseDTO responseDTO = chattingQueryServiceImpl.createChattingRoom(myId, requestDTO);
        return ApiResponse.onSuccess(SuccessStatus.CHAT_OK, responseDTO);
    }

    // 채팅방 목록 조회
    @GetMapping("/rooms")
    @Operation(summary = "채팅방 목록 조회 API", description = "사용자의 채팅방 목록 조회 API")
    public ApiResponse<ChattingResponseDTO.ChattingRoomListDTO> getChattingRoomList(@RequestParam Long memberId) {
        ChattingResponseDTO.ChattingRoomListDTO resultDTO = chattingQueryServiceImpl.getChattingRoomList(memberId);
        return ApiResponse.onSuccess(SuccessStatus.CHAT_OK, resultDTO);
    }

    // 채팅 내역 조회
    @GetMapping("/rooms/{chatRoomId}/messages")
    @Operation(summary = "채팅방 상세 조회 API", description = "특정 채팅방의 정보와 채팅 메시지 내역을 조회")
    public ApiResponse<ChattingResponseDTO.ChattingRoomFullResponseDTO> getChattingRoomDetails(
            @PathVariable Long chatRoomId,
            @RequestParam Long memberId) {
        ChattingResponseDTO.ChattingRoomFullResponseDTO fullResponse = chattingQueryServiceImpl.getFullChattingRoomDetails(chatRoomId, memberId);
        return ApiResponse.onSuccess(SuccessStatus.CHAT_OK, fullResponse);
    }

    //웹소켓 연결 후 채팅 전송 방법
    @MessageMapping("/chat.{chatRoomId}")
    @SendTo("/subscribe/chat.{chatRoomId}")
    @Operation(summary = "WebSocket 채팅 메시지 전송", description = "채팅방에 메시지를 전송하고 채팅방을 구독한 구독자에게 브로드캐스트 함")
    public ChattingRequestDTO.ChatMessageDTO sendMessage(ChattingRequestDTO.ChatMessageDTO request, @DestinationVariable Long chatRoomId) {
        log.info("WebSocket 메시지 수신: chatRoomId={}, username={}, content={}",
                chatRoomId, request.getUsername(), request.getContent());
        return chattingQueryServiceImpl.saveMessage(chatRoomId, request);
    }

    // 채팅방 삭제
    @DeleteMapping("/{chatRoomId}")
    @Operation(summary = "채팅방 삭제 API", description = "특정 채팅방 삭제 API")
    public ApiResponse<Void> deleteChattingRoom(@PathVariable Long chatRoomId) {
        chattingQueryServiceImpl.deleteChattingRoom(chatRoomId);
        return ApiResponse.onSuccess(SuccessStatus.CHAT_OK, null);
    }
}