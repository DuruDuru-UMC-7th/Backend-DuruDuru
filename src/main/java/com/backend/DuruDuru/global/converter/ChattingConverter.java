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
        String tradeType = chattingRoom.getTrade().getTradeType().toString();
        String location = chattingRoom.getTrade().getMember().getTown() != null ?
                chattingRoom.getTrade().getMember().getTown().getEupmyeondong() : "";

        // 최신 메시지 가져오기
        Message lastMessage = chattingRoom.getChattings().stream()
                .flatMap(chatting -> chatting.getMessages().stream())
                .max((m1, m2) -> m1.getSentTime().compareTo(m2.getSentTime()))
                .orElse(null);

        String lastMessageContent = lastMessage != null ? lastMessage.getContent() : "";
        LocalDateTime lastMessageDate = lastMessage != null ? lastMessage.getSentTime() : null;
        LocalDateTime sentTime = lastMessageDate;

        // 읽지 않은 메시지 수 계산
        int unreadCount = (int) chattingRoom.getChattings().stream()
                .flatMap(chatting -> chatting.getMessages().stream())
                .filter(message -> !message.isRead() && !message.getMember().getMemberId().equals(currentMemberId))
                .count();

        // 현재 사용자의 정보 찾기
        Member myUser = chattingRoom.getChattings().stream()
                .map(Chatting::getMember)
                .filter(member -> member.getMemberId().equals(currentMemberId))
                .findFirst()
                .orElse(null);

        // 상대방의 정보 찾기
        Member otherUser = chattingRoom.getChattings().stream()
                .map(Chatting::getMember)
                .filter(member -> !member.getMemberId().equals(currentMemberId))
                .findFirst()
                .orElse(null);

        String myNickname = myUser != null ? myUser.getNickName() : "알 수 없음";
        String otherNickname = otherUser != null ? otherUser.getNickName() : "알 수 없음";

        String memberImgUrl = otherUser != null && otherUser.getMemberImg() != null ?
                otherUser.getMemberImg().getUrl() : null;

        return ChattingResponseDTO.ChattingRoomDetailDTO.builder()
                .chatRoomId(chattingRoom.getChattingRoomId())
                .myNickname(myNickname)  // ✅ 내 닉네임 설정
                .otherNickname(otherNickname)  // ✅ 상대방 닉네임 설정
                .tradeType(tradeType)
                .location(location)
                .lastMessage(lastMessageContent)
                .lastMessageDate(lastMessageDate)
                .unreadCount(unreadCount)
                .sentTime(sentTime)
                .memberImgUrl(memberImgUrl)
                .build();
    }


    public static ChattingResponseDTO.ChattingRoomListDTO toChattingRoomListDTO(List<ChattingRoom> chattingRooms, Long currentMemberId) {
        List<ChattingResponseDTO.ChattingRoomDetailDTO> detailDTOList = chattingRooms.stream()
                .map(chattingRoom -> toChattingRoomDetailDTO(chattingRoom, currentMemberId))
                .sorted((d1, d2) -> {
                    LocalDateTime t1 = d1.getLastMessageDate();
                    LocalDateTime t2 = d2.getLastMessageDate();
                    if(t1 == null && t2 == null) return 0;
                    if(t1 == null) return 1;
                    if(t2 == null) return -1;
                    return t2.compareTo(t1);
                })
                .collect(Collectors.toList());

        return ChattingResponseDTO.ChattingRoomListDTO.builder()
                .chatRooms(detailDTOList)
                .count(detailDTOList.size())
                .build();
    }

    public static ChattingResponseDTO.ChatMessageResponseDTO toChatMessageResponseDTO(Message message) {
        return ChattingResponseDTO.ChatMessageResponseDTO.builder()
                .username(message.getMember().getNickName())
                .content(message.getContent())
                .sentTime(message.getSentTime())
                .build();
    }

    public static ChattingResponseDTO.ChattingRoomMakeResponseDTO toResponseDTO(ChattingRoom chattingRoom, String myNickname) {
        Trade trade = chattingRoom.getTrade();
        Member other = trade.getMember();
        String tradeImgUrl = trade.getTradeImgs().isEmpty() ? null : trade.getTradeImgs().get(0).getTradeImgUrl();
        String tradeTitle = trade.getTitle();
        String otherMemberImgUrl = other.getMemberImg() == null ? null : other.getMemberImg().getUrl();
        String otherLocation = "";
        if (other.getTown() != null) {
            otherLocation = other.getTown().getEupmyeondong();
        }

        return ChattingResponseDTO.ChattingRoomMakeResponseDTO.builder()
                .chattingRoomId(chattingRoom.getChattingRoomId())
                .myNickname(myNickname)
                .otherNickname(other.getNickName())
                .tradeImgUrl(tradeImgUrl)
                .tradeType(trade.getTradeType().toString())
                .tradeTitle(tradeTitle)
                .createdAt(chattingRoom.getCreatedAt())
                .otherMemberImgUrl(otherMemberImgUrl)
                .otherLocation(otherLocation)
                .build();
    }

    public static ChattingResponseDTO.ChattingRoomFullResponseDTO toFullResponseDTO(ChattingRoom chattingRoom, List<ChattingResponseDTO.ChatMessageResponseDTO> messages, Long myId) {
        Trade trade = chattingRoom.getTrade();
        Member tradeUser = trade.getMember();

        // 현재 사용자의 정보 찾기
        Member myUser = chattingRoom.getChattings().stream()
                .map(Chatting::getMember)
                .filter(member -> member.getMemberId().equals(myId))
                .findFirst()
                .orElse(null);

        // 상대방의 정보 찾기
        Member otherUser = chattingRoom.getChattings().stream()
                .map(Chatting::getMember)
                .filter(member -> !member.getMemberId().equals(myId))
                .findFirst()
                .orElse(null);

        String myNickname = myUser != null ? myUser.getNickName() : "알 수 없음";
        String otherNickname = otherUser != null ? otherUser.getNickName() : tradeUser.getNickName();

        String tradeImgUrl = !trade.getTradeImgs().isEmpty() ? trade.getTradeImgs().get(0).getTradeImgUrl() : null;
        String tradeTitle = trade.getTitle();
        String tradeStatus = trade.getStatus().toString();
        Long ingredientCount = trade.getIngredientCount();
        LocalDateTime expirationDate = null;

        return ChattingResponseDTO.ChattingRoomFullResponseDTO.builder()
                .chattingRoomId(chattingRoom.getChattingRoomId())
                .myNickname(myNickname)
                .otherNickname(otherNickname)
                .tradeImgUrl(tradeImgUrl)
                .tradeType(trade.getTradeType().toString())
                .tradeTitle(tradeTitle)
                .createdAt(chattingRoom.getCreatedAt())
                .otherMemberImgUrl(otherUser != null && otherUser.getMemberImg() != null ?
                        otherUser.getMemberImg().getUrl() : null)
                .otherLocation(otherUser != null && otherUser.getTown() != null ?
                        otherUser.getTown().getEupmyeondong() : "")
                .tradeStatus(tradeStatus)
                .ingredientCount(ingredientCount)
                .expirationDate(expirationDate)
                .chatMessages(messages)
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