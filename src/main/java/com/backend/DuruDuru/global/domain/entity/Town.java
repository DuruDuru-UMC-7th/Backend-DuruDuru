package com.backend.DuruDuru.global.domain.entity;

import com.backend.DuruDuru.global.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

    // 동네에 등록된 사용자 목록
    @OneToMany(mappedBy = "town", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();
}
