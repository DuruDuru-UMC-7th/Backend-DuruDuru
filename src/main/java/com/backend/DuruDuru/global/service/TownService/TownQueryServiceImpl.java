package com.backend.DuruDuru.global.service.TownService;


import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.AuthException;
import com.backend.DuruDuru.global.apiPayload.exception.handler.MemberException;
import com.backend.DuruDuru.global.apiPayload.exception.handler.TownHandler;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Town;
import com.backend.DuruDuru.global.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TownQueryServiceImpl implements TownQueryService{

    private final MemberRepository memberRepository;

    // Town 조회
    @Override
    @Transactional
    public Town findTownByMember(Member member) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
        validateLoggedInMember(member);

        if(member.getTown() == null) {
            throw new TownHandler(ErrorStatus.TOWN_NOT_REGISTERED);
        }

        return member.getTown();
    }

    // 로그인 여부 확인
    private void validateLoggedInMember(Member member) {
        if (member == null) {
            throw new AuthException(ErrorStatus.LOGIN_REQUIRED);
        }
    }
}
