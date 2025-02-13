package com.backend.DuruDuru.global.repository;

import com.backend.DuruDuru.global.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChattingChattingRoomChattingRoomIdOrderBySentTimeAsc(Long chatRoomId);
}