package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.web.dto.Member.MemberResponseDTO;

public class MemberConverter {

    public static MemberResponseDTO.EmailLoginResponseDTO toEmailLoginResponse(String accessToken, String refreshToken) {
        return MemberResponseDTO.EmailLoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static MemberResponseDTO.TokenRefreshResponseDTO toTokenRefreshResponse(
            String accessToken, String refreshToken) {
        return MemberResponseDTO.TokenRefreshResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
