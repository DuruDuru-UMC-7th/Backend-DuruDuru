package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.ChattingRoom;
import com.backend.DuruDuru.global.web.dto.Chatting.ChattingResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChattingRepository extends JpaRepository<ChattingRoom, Long> {
    @Query("select distinct cr from ChattingRoom cr join cr.chattings c where c.member.memberId = :memberId")
    List<ChattingRoom> findChattingRoomsByMemberId(@Param("memberId") Long memberId);

    @Query("select m from Message m where m.chatting.chattingRoom.chattingRoomId = :chatRoomId order by m.sentTime asc")
    List<ChattingResponseDTO.ChatMessageResponseDTO> findByChatRoomIdOrderBySentTimeAsc(@Param("chatRoomId") Long chatRoomId);

}
