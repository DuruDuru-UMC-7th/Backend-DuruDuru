package com.backend.DuruDuru.global.service.MemberService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.web.dto.Member.EmailLoginRequestDTO;
import com.backend.DuruDuru.global.web.dto.Member.EmailLoginResponseDTO;
import com.backend.DuruDuru.global.web.dto.Member.EmailRegisterRequestDTO;

public interface MemberCommandService {

    Member findMemberById(Long memberId);
    void emailRegister(EmailRegisterRequestDTO request);
    EmailLoginResponseDTO emailLogin(EmailLoginRequestDTO request);
}
