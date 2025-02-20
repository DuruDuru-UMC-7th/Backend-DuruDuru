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
        private String myNickname;
        private String otherNickname;
        private String tradeType;
        private String location;
        private String lastMessage;
        private LocalDateTime lastMessageDate;
        private int unreadCount;
        private LocalDateTime sentTime;
        private String memberImgUrl;
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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChattingRoomMakeResponseDTO {
        private Long chattingRoomId;
        private String myNickname;
        private String otherNickname;
        private String tradeImgUrl;
        private String tradeType;
        private LocalDateTime createdAt;
        private String otherMemberImgUrl;
        private String otherLocation;
        private String tradeStatus;
        private String tradeTitle;
        private Long ingredientCount;
        private LocalDateTime expirationDate;
        private List<ChattingResponseDTO.ChatMessageResponseDTO> chatMessages;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChattingRoomFullResponseDTO {
        private Long chattingRoomId;
        private String myNickname;
        private String otherNickname;
        private String tradeImgUrl;
        private String tradeType;
        private String tradeTitle;
        private LocalDateTime createdAt;
        private String otherMemberImgUrl;
        private String otherLocation;
        private String tradeStatus;
        private Long ingredientCount;
        private LocalDateTime expirationDate;
        private List<ChatMessageResponseDTO> chatMessages;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChatMessageResponseDTO {
        private String username;
        private String content;
        private LocalDateTime sentTime;
    }

}
