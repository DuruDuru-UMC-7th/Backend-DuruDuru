package com.backend.DuruDuru.global.service.MemberService;

import com.backend.DuruDuru.global.domain.entity.Member;

public interface MemberCommandService {

    Member findMemberById(Long memberId);

}
