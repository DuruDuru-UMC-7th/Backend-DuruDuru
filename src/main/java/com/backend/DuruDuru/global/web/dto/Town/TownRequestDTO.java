package com.backend.DuruDuru.global.web.dto.Town;

import lombok.Getter;

public class TownRequestDTO {

    @Getter
    public static class ToTownRequestDTO {
        double latitude;
        double longitude;
        String city;
        String district;
        String townName;
    }
}
