package com.backend.DuruDuru.global.service.TownService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Town;
import com.backend.DuruDuru.global.web.dto.Town.TownRequestDTO;

public interface TownCommandService {
    // Town 엔티티 저장
    Town createTown(Member member, TownRequestDTO.ToTownRequestDTO request);
    // Town 정보 수정
    Town updateTown(Member member, TownRequestDTO.ToTownRequestDTO request);
}
