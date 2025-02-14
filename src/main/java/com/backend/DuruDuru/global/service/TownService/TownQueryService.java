package com.backend.DuruDuru.global.service.TownService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Town;


public interface TownQueryService {

    // Town 조회
    Town findTownByMember(Member member);
}
