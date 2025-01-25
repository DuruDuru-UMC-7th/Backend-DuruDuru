package com.backend.DuruDuru.global.converter;


import com.backend.DuruDuru.global.domain.entity.Town;
import com.backend.DuruDuru.global.web.dto.Town.TownRequestDTO;
import com.backend.DuruDuru.global.web.dto.Town.TownResponseDTO;

public class TownConverter {

    public static TownResponseDTO.TownResultDTO toTownResponseDTO(Town town) {
        return TownResponseDTO.TownResultDTO.builder()
                .latitude(town.getLatitude())
                .longitude(town.getLongitude())
                .sido(town.getSido())
                .sigungu(town.getSigungu())
                .eupmyeondong(town.getEupmyeondong())
                .build();
    }

    public static Town toTown(TownRequestDTO.ToTownRequestDTO request) {
        return Town.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .sido(request.getSido())
                .sigungu(request.getSigungu())
                .eupmyeondong(request.getEupmyeondong())
                .build();
    }
}
