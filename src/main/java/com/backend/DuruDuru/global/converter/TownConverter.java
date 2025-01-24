package com.backend.DuruDuru.global.converter;


import com.backend.DuruDuru.global.domain.entity.Town;
import com.backend.DuruDuru.global.web.dto.Town.TownRequestDTO;
import com.backend.DuruDuru.global.web.dto.Town.TownResponseDTO;

public class TownConverter {

    public static TownResponseDTO.TownResultDTO toTownResponseDTO(Town town) {
        return TownResponseDTO.TownResultDTO.builder()
                .latitude(town.getLatitude())
                .longitude(town.getLongitude())
                .city(town.getCity())
                .district(town.getDistrict())
                .townName(town.getTownName())
                .build();
    }

    public static Town toTown(TownRequestDTO.ToTownRequestDTO request) {
        return Town.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .city(request.getCity())
                .district(request.getDistrict())
                .townName(request.getTownName())
                .build();
    }
}
