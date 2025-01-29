package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.web.dto.AuthDTO.KakaoProfile;
import com.backend.DuruDuru.global.web.dto.Member.AuthResponseDTO;

public class AuthConverter {

    public static Member toMember(KakaoProfile kakaoProfile, String nickname) {
        return Member.builder()
                .nickName(nickname)
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .build();
    }

    public static AuthResponseDTO.OAuthResponse toOAuthResponse(
            String accessToken, String refreshToken, Member member) {
        return AuthResponseDTO.OAuthResponse.builder()
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
