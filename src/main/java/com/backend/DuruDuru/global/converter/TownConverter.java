package com.backend.DuruDuru.global.converter;


import com.backend.DuruDuru.global.web.dto.Town.TownResponseDTO;

public class TownConverter {

    public static TownResponseDTO.CoordsResponseDTO toCoordsResponseDTO(double latitude, double longitude) {
        return TownResponseDTO.CoordsResponseDTO.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();

    }
}
