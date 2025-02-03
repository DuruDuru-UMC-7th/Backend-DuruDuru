package com.backend.DuruDuru.global.web.dto.Town;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TownResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TownResultDTO {
        Long townId;
        Long memberId;
        double latitude;
        double longitude;
        String sido;
        String sigungu;
        String eupmyeondong;
    }
}
