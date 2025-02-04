package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import com.backend.DuruDuru.global.web.dto.Town.TownRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Town extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "town_id", nullable = false, columnDefinition = "bigint")
    private Long townId;

    @Column(name = "sido", nullable = false, columnDefinition = "varchar(50)")
    private String sido;

    @Column(name = "sigungu", nullable = false, columnDefinition = "varchar(50)")
    private String sigungu;

    @Column(name = "eupmyeondong", nullable = false, columnDefinition = "varchar(50)")
    private String eupmyeondong;

    @Column(name = "latitude", nullable = false, columnDefinition = "double precision")
    private Double latitude;

    @Column(name = "longitude", nullable = false, columnDefinition = "double precision")
    private Double longitude;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void update(TownRequestDTO.ToTownRequestDTO request) {
        this.sido = request.getSido();
        this.sigungu = request.getSigungu();
        this.eupmyeondong = request.getEupmyeondong();
        this.latitude = request.getLatitude();
        this.longitude = request.getLongitude();
    }

}
