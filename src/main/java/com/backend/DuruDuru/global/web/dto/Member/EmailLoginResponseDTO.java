package com.backend.DuruDuru.global.web.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailLoginResponseDTO {
    private String accessToken;
    private String refreshToken;

}
