package com.backend.DuruDuru.global.service.ChattingService;

import com.backend.DuruDuru.global.web.dto.Chatting.ChattingResponseDTO;

public interface ChattingQueryService {
    ChattingResponseDTO.ChattingRoomListDTO getChattingRoomList(Long memberId);
}
