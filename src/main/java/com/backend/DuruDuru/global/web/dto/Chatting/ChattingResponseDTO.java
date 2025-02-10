package com.backend.DuruDuru.global.web.dto.Chatting;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ChattingResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChattingRoomDetailDTO {
        private Long chatRoomId;
        private String username;
        private String tradeType;
        private String location;
        private String lastMessage;
        private LocalDateTime lastMessageDate;
        private int unreadCount;
        private LocalDateTime sentTime;
    }

    //ChttingRoom상세 정보 담아서 개수와 함께 반환 하는 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChattingRoomListDTO {
        private int count;
        private List<ChattingRoomDetailDTO> chatRooms;
    }
}
