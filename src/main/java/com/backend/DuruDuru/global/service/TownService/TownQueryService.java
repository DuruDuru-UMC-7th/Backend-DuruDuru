package com.backend.DuruDuru.global.service.TownService;

import com.backend.DuruDuru.global.domain.entity.Town;


public interface TownQueryService {

    // MemberId로 Town 조회
    Town findTownByMember(Long id);
}
