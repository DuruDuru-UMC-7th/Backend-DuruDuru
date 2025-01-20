package com.backend.DuruDuru.global.web.dto.Member;

import lombok.*;

public class MemberResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EmailLoginResponseDTO {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TokenRefreshResponseDTO {
        String accessToken;
        String refreshToken;
    }
}
