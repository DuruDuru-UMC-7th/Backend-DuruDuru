package com.backend.DuruDuru.global.web.dto.Chatting;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public static class ChatMessageDTO {
        private String username;         // 메시지를 보낸 사람의 이름
        private String content;          // 메시지 내용
        private LocalDateTime sentTime;  // 메시지 전송 시간
    }


}
