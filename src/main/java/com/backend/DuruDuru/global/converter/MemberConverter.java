package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.web.dto.Member.AuthResponseDTO;

public class MemberConverter {

    public static AuthResponseDTO.EmailLoginResponse toEmailLoginResponse(String accessToken, String refreshToken, Member member) {
        return AuthResponseDTO.EmailLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getMemberId())
                .build();
    }

    public static AuthResponseDTO.TokenRefreshResponse toTokenRefreshResponse(
            String accessToken, String refreshToken) {
        return AuthResponseDTO.TokenRefreshResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
