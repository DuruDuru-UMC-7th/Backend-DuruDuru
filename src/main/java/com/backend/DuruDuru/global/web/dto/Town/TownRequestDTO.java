package com.backend.DuruDuru.global.web.dto.Town;

import lombok.Getter;

public class TownRequestDTO {

    @Getter
    public static class CoordsRequestDTO {
        double latitude;
        double longitude;
    }
}
