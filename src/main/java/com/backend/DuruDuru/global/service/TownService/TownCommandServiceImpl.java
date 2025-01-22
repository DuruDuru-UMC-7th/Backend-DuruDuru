package com.backend.DuruDuru.global.service.TownService;


import com.backend.DuruDuru.global.domain.entity.Town;
import com.backend.DuruDuru.global.web.dto.Town.TownRequestDTO;

public class TownCommandServiceImpl implements TownCommandService {
    @Override
    public String coordsToTownName(TownRequestDTO.CoordsRequestDTO request) {
        // 위도 경도를 통해 행정동을 반환
        return null;
    }

    @Override
    public Town registerTown() {
        return null;
    }
}
