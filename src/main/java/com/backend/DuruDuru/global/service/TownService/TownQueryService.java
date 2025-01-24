package com.backend.DuruDuru.global.service.TownService;

import com.backend.DuruDuru.global.domain.entity.Town;


public interface TownQueryService {

    Town findTownByMember(Long id);
}
