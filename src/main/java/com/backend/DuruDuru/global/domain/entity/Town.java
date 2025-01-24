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

    @Column(name = "city", nullable = false, columnDefinition = "varchar(50)")
    private String city;

    @Column(name = "district", nullable = false, columnDefinition = "varchar(50)")
    private String district;

    @Column(name = "town_name", nullable = false, columnDefinition = "varchar(50)")
    private String townName;

    @Column(name = "latitude", nullable = false, columnDefinition = "double precision")
    private Double latitude;

    @Column(name = "longitude", nullable = false, columnDefinition = "double precision")
    private Double longitude;

    @OneToOne(mappedBy = "town")
    private Member member;

    public void update(TownRequestDTO.ToTownRequestDTO request) {
        this.city = request.getCity();
        this.district = request.getDistrict();
        this.townName = request.getTownName();
        this.latitude = request.getLatitude();
        this.longitude = request.getLongitude();
    }

}
