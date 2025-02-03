package com.backend.DuruDuru.global.converter;


import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Town;
import com.backend.DuruDuru.global.web.dto.Town.TownRequestDTO;
import com.backend.DuruDuru.global.web.dto.Town.TownResponseDTO;

public class TownConverter {

    public static TownResponseDTO.TownResultDTO toTownResponseDTO(Town town) {
        return TownResponseDTO.TownResultDTO.builder()
                .townId(town.getTownId())
                .memberId(town.getMember().getMemberId())
                .latitude(town.getLatitude())
                .longitude(town.getLongitude())
                .sido(town.getSido())
                .sigungu(town.getSigungu())
                .eupmyeondong(town.getEupmyeondong())
                .build();
    }

    public static Town toCreateTown(TownRequestDTO.ToTownRequestDTO request, Member member) {
        return Town.builder()
                .member(member)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .sido(request.getSido())
                .sigungu(request.getSigungu())
                .eupmyeondong(request.getEupmyeondong())
                .build();
    }
}
