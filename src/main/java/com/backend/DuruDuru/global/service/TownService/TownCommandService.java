package com.backend.DuruDuru.global.service.TownService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Town;
import com.backend.DuruDuru.global.web.dto.Town.TownRequestDTO;

public interface TownCommandService {

    // Town 엔티티를 저장하는 메서드
    Town createTown(Member member, TownRequestDTO.ToTownRequestDTO request);
    // Town 정보를 수정하는 메서드
    Town updateTown(Member member, TownRequestDTO.ToTownRequestDTO request);

}
