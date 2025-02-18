package com.backend.DuruDuru.global.service.ChattingService;

import com.backend.DuruDuru.global.web.dto.Chatting.ChattingRequestDTO;
import com.backend.DuruDuru.global.web.dto.Chatting.ChattingResponseDTO;

public interface ChattingQueryService {
    ChattingResponseDTO.ChattingRoomListDTO getChattingRoomList(Long memberId);
    ChattingResponseDTO.ChattingRoomMakeResponseDTO createChattingRoom(Long myId, ChattingRequestDTO.ChattingRoomMakeRequestDTO requestDTO);
    ChattingResponseDTO.ChattingRoomFullResponseDTO getFullChattingRoomDetails(Long chatRoomId, Long currentMemberId);
    ChattingResponseDTO.ChatMessageResponseDTO saveMessage(Long chatRoomId, ChattingRequestDTO.ChatMessageResquestDTO request);
    void deleteChattingRoom(Long chattingRoomId);
}
