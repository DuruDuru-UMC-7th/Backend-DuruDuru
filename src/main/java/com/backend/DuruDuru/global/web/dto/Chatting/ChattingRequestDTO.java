package com.backend.DuruDuru.global.web.dto.Chatting;

import lombok.*;

import java.time.LocalDateTime;

public class ChattingRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChattingRoomMakeRequestDTO {
       private Long tradeId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChatMessageResquestDTO {
        private String username;
        private String content;
    }



}
