package com.backend.DuruDuru.global.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false, columnDefinition = "bigint")
    private Long messageId;

    @Column(name = "content", nullable = false, columnDefinition = "varchar(500)")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_id", nullable = false)
    private Chatting chatting;

    @Column(name = "sent_time", nullable = false)
    private LocalDateTime sentTime;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
