package com.backend.DuruDuru.global.service.MemberService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.web.dto.Member.MemberRequestDTO;
import com.backend.DuruDuru.global.web.dto.Member.MemberResponseDTO;

public interface MemberCommandService {

    Member findMemberById(Long memberId);
    void emailRegister(MemberRequestDTO.EmailRegisterRequestDTO request);
    MemberResponseDTO.EmailLoginResponseDTO emailLogin(MemberRequestDTO.EmailLoginRequestDTO request);
    MemberResponseDTO.TokenRefreshResponseDTO refreshToken(String refreshToken);
}
