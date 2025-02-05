package com.backend.DuruDuru.global.web.dto.Town;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TownRequestDTO {

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ToTownRequestDTO {
        private double latitude;
        private double longitude;
        private String sido;
        private String sigungu;
        private String eupmyeondong;
    }
}
