package com.backend.DuruDuru.global.service.ChattingService;

import com.backend.DuruDuru.global.web.dto.Chatting.ChattingRequestDTO;
import com.backend.DuruDuru.global.web.dto.Chatting.ChattingResponseDTO;

public interface ChattingQueryService {
    ChattingResponseDTO.ChattingRoomListDTO getChattingRoomList(Long memberId);
    ChattingResponseDTO.ChattingRoomMakeResponseDTO createChattingRoom(Long myId, ChattingRequestDTO.ChattingRoomMakeRequestDTO requestDTO);
    ChattingResponseDTO.ChattingRoomFullResponseDTO getFullChattingRoomDetails(Long chatRoomId, Long currentMemberId);
    ChattingRequestDTO.ChatMessageDTO saveMessage(Long chatRoomId, ChattingRequestDTO.ChatMessageDTO request);
}
