package com.backend.DuruDuru.global.service.ChattingService;

import com.backend.DuruDuru.global.converter.ChattingConverter;
import com.backend.DuruDuru.global.domain.entity.ChattingRoom;
import com.backend.DuruDuru.global.repository.ChattingRepository;
import com.backend.DuruDuru.global.web.dto.Chatting.ChattingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChattingQueryServiceImpl implements ChattingQueryService {

    private final ChattingRepository chattingRoomRepository;

    //회원이 참여한 채팅방 조회
    @Override
    public ChattingResponseDTO.ChattingRoomListDTO getChattingRoomList(Long memberId) {
        List<ChattingRoom> chattingRooms = chattingRoomRepository.findChattingRoomsByMemberId(memberId);
        return ChattingConverter.toChattingRoomListDTO(chattingRooms, memberId);
    }
}