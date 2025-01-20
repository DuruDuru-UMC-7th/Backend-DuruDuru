package com.backend.DuruDuru.global.service.MemberService;

import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.web.dto.Member.EmailRegisterRequestDTO;
import com.backend.DuruDuru.global.web.dto.Member.EmailRegisterResponseDTO;

public interface MemberCommandService {

    Member findMemberById(Long memberId);

    void registerMember(EmailRegisterRequestDTO request);

}
