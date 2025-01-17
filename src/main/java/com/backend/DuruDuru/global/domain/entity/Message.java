package com.backend.DuruDuru.global.domain.entity;

import jakarta.persistence.*;
import lombok.*;

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
}
