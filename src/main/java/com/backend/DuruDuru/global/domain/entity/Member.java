package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import com.backend.DuruDuru.global.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, columnDefinition = "bigint")
    private Long memberId;

    @Column(length = 45, unique = true)
    private String email;

    private String password;

    @Column(name = "nickName", nullable = false, columnDefinition = "varchar(200)")
    private String nickName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long age;

    @ColumnDefault("0") // 삭제시 1
    private int isDelete;

    private String accessToken;
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "town_id")
    private Town town;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_img_id")
    private MemberImg memberImg;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fridge_id")
    private Fridge fridge;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Trade> trades = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Chatting> chattings = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberRecipe> memberRecipes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<LikeTrade> likeTrades = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receipt> receipts = new ArrayList<>();

    public void addReceipt(Receipt receipt) {
        this.receipts.add(receipt);
        if (receipt.getMember() != this) {
            receipt.setMember(this);
        }
    }


    public void updateToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void setFridge(Fridge fridge) {
        this.fridge = fridge;
        if (fridge.getMember() != this) {
            fridge.setMember(this);
        }
    }

    public Long getFridgeId() {
        return fridge != null ? fridge.getFridgeId() : null;
    }


}
