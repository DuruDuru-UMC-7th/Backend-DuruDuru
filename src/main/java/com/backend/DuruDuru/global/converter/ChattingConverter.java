package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.*;
import com.backend.DuruDuru.global.web.dto.Chatting.ChattingRequestDTO;
import com.backend.DuruDuru.global.web.dto.Chatting.ChattingResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChattingConverter {

    public static ChattingResponseDTO.ChattingRoomDetailDTO toChattingRoomDetailDTO(ChattingRoom chattingRoom, Long currentMemberId) {
        // 거래유형 가져오기
        String tradeType = chattingRoom.getTrade().getTradeType().toString();

        // 위치 가져오기
        String location = "";
        if (chattingRoom.getTrade().getMember().getTown() != null) {
            location = chattingRoom.getTrade().getMember().getTown().getEupmyeondong();
        }

        // 최신 메시지 찾기
        Message lastMessage = null;
        for (Chatting chatting : chattingRoom.getChattings()) {
            for (Message message : chatting.getMessages()) {
                if (lastMessage == null || message.getSentTime().isAfter(lastMessage.getSentTime())) {
                    lastMessage = message;
                }
            }
        }
        String lastMessageContent = lastMessage != null ? lastMessage.getContent() : "";
        LocalDateTime lastMessageDate = lastMessage != null ? lastMessage.getSentTime() : null;
        LocalDateTime sentTime = lastMessageDate;

        // 읽은 메시지 계산
        int unreadCount = 0;
        for (Chatting chatting : chattingRoom.getChattings()) {
            for (Message message : chatting.getMessages()) {
                if (!message.isRead() && !message.getMember().getMemberId().equals(currentMemberId)) {
                    unreadCount++;
                }
            }
        }

        // 마지막 메시지가 존재하면 발신자 닉네임 가져오기
        String username = (lastMessage != null)
                ? lastMessage.getMember().getNickName()
                : chattingRoom.getTrade().getMember().getNickName();

        return ChattingResponseDTO.ChattingRoomDetailDTO.builder()
                .chatRoomId(chattingRoom.getChattingRoomId())
                .username(username)
                .tradeType(tradeType)
                .location(location)
                .lastMessage(lastMessageContent)
                .lastMessageDate(lastMessageDate)
                .unreadCount(unreadCount)
                .sentTime(sentTime)
                .build();
    }

    public static ChattingResponseDTO.ChattingRoomListDTO toChattingRoomListDTO(List<ChattingRoom> chattingRooms, Long currentMemberId) {
        List<ChattingResponseDTO.ChattingRoomDetailDTO> detailDTOList = chattingRooms.stream()
                .map(chattingRoom -> toChattingRoomDetailDTO(chattingRoom, currentMemberId))
                .collect(Collectors.toList());

        return ChattingResponseDTO.ChattingRoomListDTO.builder()
                .chatRooms(detailDTOList)
                .count(detailDTOList.size())
                .build();
    }

    public static ChattingResponseDTO.ChattingRoomMakeResponseDTO toResponseDTO(ChattingRoom chattingRoom) {
        Trade trade = chattingRoom.getTrade();
        Member other = trade.getMember();

        // tradeImgs가 있다면 첫 번째 이미지 URL 사용
        String tradeImgUrl = trade.getTradeImgs().isEmpty() ? null : trade.getTradeImgs().get(0).getTradeImgUrl();

        String tradeTitle = trade.getTitle();

        String otherMemberImgUrl = other.getMemberImg() == null ? null : other.getMemberImg().getUrl();

        String otherLocation = "";
        if (other.getTown() != null) {
            otherLocation = other.getTown().getEupmyeondong();
        }

        return ChattingResponseDTO.ChattingRoomMakeResponseDTO.builder()
                .chattingRoomId(chattingRoom.getChattingRoomId())
                .otherNickname(other.getNickName())
                .tradeImgUrl(tradeImgUrl)
                .tradeType(trade.getTradeType().toString())
                .tradeTitle(tradeTitle)
                .createdAt(chattingRoom.getCreatedAt())
                .otherMemberImgUrl(otherMemberImgUrl)
                .otherLocation(otherLocation)
                .build();
    }


    //채팅 메시지 전체 조회
    public static ChattingResponseDTO.ChattingRoomFullResponseDTO toFullResponseDTO(ChattingRoom chattingRoom, List<ChattingResponseDTO.ChatMessageResponseDTO> messages) {
        Trade trade = chattingRoom.getTrade();
        Member other = trade.getMember(); // Trade에 저장된 상대방

        String tradeImgUrl = (trade.getTradeImgs() != null && !trade.getTradeImgs().isEmpty())
                ? trade.getTradeImgs().get(0).getTradeImgUrl() : null;
        String tradeTitle = trade.getTitle();
        String otherMemberImgUrl = (other.getMemberImg() != null) ? other.getMemberImg().getUrl() : null;
        String otherLocation = "";
        if (other.getTown() != null) {
            otherLocation = other.getTown().getEupmyeondong();
        }
        String tradeStatus = trade.getStatus().toString();
        Long ingredientCount = trade.getIngredientCount();
        LocalDateTime expirationDate = null;

        // 채팅 메시지 목록 변환
        List<ChattingResponseDTO.ChatMessageResponseDTO> chatMessages = messages.stream()
                .map(message -> ChattingResponseDTO.ChatMessageResponseDTO.builder()
                        .username(message.getUsername()) // 수정된 부분
                        .content(message.getContent())
                        .sentTime(message.getSentTime())
                        .build())
                .collect(Collectors.toList());

        return ChattingResponseDTO.ChattingRoomFullResponseDTO.builder()
                .chattingRoomId(chattingRoom.getChattingRoomId())
                .otherNickname(other.getNickName())
                .tradeImgUrl(tradeImgUrl)
                .tradeType(trade.getTradeType().toString())
                .tradeTitle(tradeTitle)
                .createdAt(chattingRoom.getCreatedAt())
                .otherMemberImgUrl(otherMemberImgUrl)
                .otherLocation(otherLocation)
                .tradeStatus(tradeStatus)
                .ingredientCount(ingredientCount)
                .expirationDate(expirationDate)
                .chatMessages(chatMessages)
                .build();
    }

    public ChattingResponseDTO.ChatMessageResponseDTO toResponse(Message message) {
        return ChattingResponseDTO.ChatMessageResponseDTO.builder()
                .username(message.getMember().getNickName())
                .content(message.getContent())
                .sentTime(message.getSentTime())
                .build();
    }
}

