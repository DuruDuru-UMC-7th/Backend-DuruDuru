package com.backend.DuruDuru.global.service.ChattingService;

import com.backend.DuruDuru.global.converter.ChattingConverter;
import com.backend.DuruDuru.global.domain.entity.*;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import com.backend.DuruDuru.global.repository.ChatMessageRepository;
import com.backend.DuruDuru.global.repository.ChattingRepository;
import com.backend.DuruDuru.global.repository.MemberRepository;
import com.backend.DuruDuru.global.repository.TradeRepository;
import com.backend.DuruDuru.global.web.dto.Chatting.ChattingRequestDTO;
import com.backend.DuruDuru.global.web.dto.Chatting.ChattingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChattingQueryServiceImpl implements ChattingQueryService {

    private final TradeRepository tradeRepository;
    private final ChattingConverter chattingConverter;
    private final ChattingRepository chattingRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 회원이 참여한 채팅방 목록 조회
    @Override
    public ChattingResponseDTO.ChattingRoomListDTO getChattingRoomList(Long memberId) {
        List<ChattingRoom> chattingRooms = chattingRepository.findChattingRoomsByMemberId(memberId);
        return ChattingConverter.toChattingRoomListDTO(chattingRooms, memberId);
    }

    // 채팅방 생성
    @Override
    public ChattingResponseDTO.ChattingRoomMakeResponseDTO createChattingRoom(Long myId, ChattingRequestDTO.ChattingRoomMakeRequestDTO requestDTO) {
        Trade trade = tradeRepository.findById(requestDTO.getTradeId())
                .orElseThrow(() -> new RuntimeException("Trade not found"));

        String otherNickname = trade.getMember().getNickName();
        String tradeImgUrl = trade.getTradeImgs().isEmpty() ? null : trade.getTradeImgs().get(0).getTradeImgUrl();
        String tradeTitle = trade.getTitle();
        TradeType tradeType = trade.getTradeType();
        String otherMemberImgUrl = trade.getMember().getMemberImg() == null ? null : trade.getMember().getMemberImg().getUrl();
        String otherLocation = "";
        if (trade.getMember().getTown() != null) {
            otherLocation = trade.getMember().getTown().getEupmyeondong();
        }

        // 채팅방 이름
        String roomName = trade.getMember().getNickName() + "님과의 채팅";

        // ChattingRoom 엔티티 생성
        ChattingRoom chattingRoom = ChattingRoom.builder()
                .roomName(roomName)
                .trade(trade)
                .otherNickname(otherNickname)
                .tradeImgUrl(tradeImgUrl)
                .tradeType(tradeType)
                .tradeTitle(tradeTitle)
                .otherMemberImgUrl(otherMemberImgUrl)
                .otherLocation(otherLocation)
                .chattings(new ArrayList<>())
                .build();


//  생성한 유저를 채팅방과 연결
        Member member = memberRepository.findById(myId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Chatting chatting = Chatting.builder()
                .chattingRoom(chattingRoom)
                .member(memberRepository.findById(myId).orElseThrow(() -> new RuntimeException("Member not found")))
                .build();

        chattingRoom.getChattings().add(chatting);
        ChattingRoom savedRoom = chattingRepository.save(chattingRoom);
        member.getChattings().add(chatting);
        return chattingConverter.toResponseDTO(savedRoom);
    }


    //채팅방 정보 전체 조회
    @Override
    public ChattingResponseDTO.ChattingRoomFullResponseDTO getFullChattingRoomDetails(Long chatRoomId, Long currentMemberId) {
        ChattingRoom chattingRoom = chattingRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("ChattingRoom not found"));
        List<ChattingRequestDTO.ChatMessageDTO> messages = chattingRepository.findByChatRoomIdOrderBySentTimeAsc(chatRoomId);
        return ChattingConverter.toFullResponseDTO(chattingRoom, messages);
    }

    @Override
    public ChattingRequestDTO.ChatMessageDTO saveMessage(Long chatRoomId, ChattingRequestDTO.ChatMessageDTO request) {
        ChattingRoom chattingRoom = chattingRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("ChattingRoom not found"));

        Member member = memberRepository.findByNickName(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Message message = Message.builder()
                .content(request.getContent())
                .sentTime(LocalDateTime.now())
                .isRead(false)
                .member(member)
                .build();

        Message savedMessage = chatMessageRepository.save(message);

        return chattingConverter.toResponse(savedMessage);
    }

    @Override
    public void deleteChattingRoom(Long chattingRoomId) {
        ChattingRoom chattingRoom = chattingRepository.findById(chattingRoomId)
                .orElseThrow(() -> new RuntimeException("ChattingRoom not found"));
        chattingRepository.delete(chattingRoom);
    }

}
