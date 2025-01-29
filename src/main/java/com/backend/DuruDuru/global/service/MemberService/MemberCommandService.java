package com.backend.DuruDuru.global.service.MemberService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.web.dto.Member.AuthRequestDTO;
import com.backend.DuruDuru.global.web.dto.Member.AuthResponseDTO;

public interface MemberCommandService {

    Member findMemberById(Long memberId);
    void emailRegister(AuthRequestDTO.EmailRegisterRequest request);
    AuthResponseDTO.EmailLoginResponse emailLogin(AuthRequestDTO.EmailLoginRequest request);
    AuthResponseDTO.TokenRefreshResponse refreshToken(String refreshToken);
    AuthResponseDTO.OAuthResponse kakaoLogin(String code);
}
