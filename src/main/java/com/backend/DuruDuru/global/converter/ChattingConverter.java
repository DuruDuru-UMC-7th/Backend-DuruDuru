package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.Chatting;
import com.backend.DuruDuru.global.domain.entity.ChattingRoom;
import com.backend.DuruDuru.global.domain.entity.Message;
import com.backend.DuruDuru.global.web.dto.Chatting.ChattingResponseDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ChattingConverter {

    public static ChattingResponseDTO.ChattingRoomDetailDTO toChattingRoomDetailDTO(ChattingRoom chattingRoom, Long currentMemberId) {
        // 거래유형 가져오기
        String tradeType = chattingRoom.getTrade().getTradeType().toString();

        // 위치정보 가져오기
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
        LocalDateTime sentTime = lastMessageDate; // 마지막 메시지의 전송시간을 보낸 시간으로 사용

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

    //ChattingRoom엔티티를 ChattingRoomListDTO로 변환
    public static ChattingResponseDTO.ChattingRoomListDTO toChattingRoomListDTO(List<ChattingRoom> chattingRooms, Long currentMemberId) {
        List<ChattingResponseDTO.ChattingRoomDetailDTO> detailDTOList = chattingRooms.stream()
                .map(chattingRoom -> toChattingRoomDetailDTO(chattingRoom, currentMemberId))
                .collect(Collectors.toList());

        return ChattingResponseDTO.ChattingRoomListDTO.builder()
                .chatRooms(detailDTOList)
                .count(detailDTOList.size())
                .build();
    }
}