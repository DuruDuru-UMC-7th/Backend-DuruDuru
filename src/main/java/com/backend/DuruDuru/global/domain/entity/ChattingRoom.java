package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChattingRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_room_id", nullable = false, columnDefinition = "bigint")
    private Long chattingRoomId;

    @Column(name = "room_name", columnDefinition = "varchar(200)")
    private String roomName;

    @Column(name = "other_nickname", columnDefinition = "varchar(200)")
    private String otherNickname;

    @Column(name = "other_member_img_url", columnDefinition = "varchar(200)")
    private String otherMemberImgUrl;

    @Column(name = "other_location", columnDefinition = "varchar(200)")
    private String otherLocation;

    @Column(name = "trade_img_url", columnDefinition = "varchar(200)")
    private String tradeImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_type", columnDefinition = "varchar(50)")
    private TradeType tradeType;

    @Column(name = "trade_title", columnDefinition = "varchar(200)")
    private String tradeTitle;

    @OneToMany(mappedBy = "chattingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chatting> chattings = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id")
    private Trade trade;
}
