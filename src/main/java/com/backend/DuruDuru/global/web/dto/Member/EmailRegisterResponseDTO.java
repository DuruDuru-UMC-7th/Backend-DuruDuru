package com.backend.DuruDuru.global.web.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailRegisterResponseDTO {
    private String accessToken;
    private String refreshToken;
}
