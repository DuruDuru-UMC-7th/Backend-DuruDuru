package com.backend.DuruDuru.global.service.TownService;

import com.backend.DuruDuru.global.domain.entity.Town;
import com.backend.DuruDuru.global.web.dto.Town.TownRequestDTO;

public interface TownCommandService {

    // 위도 경도를 통해 행정동 이름 반환
    String coordsToTownName(TownRequestDTO.CoordsRequestDTO request);

    // Town 등록
    public Town registerTown();


}
