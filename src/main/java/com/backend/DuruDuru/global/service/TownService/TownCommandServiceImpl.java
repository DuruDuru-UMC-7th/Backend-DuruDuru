package com.backend.DuruDuru.global.service.TownService;


import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.handler.MemberException;
import com.backend.DuruDuru.global.apiPayload.exception.handler.TownHandler;
import com.backend.DuruDuru.global.converter.TownConverter;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.domain.entity.Town;
import com.backend.DuruDuru.global.repository.MemberRepository;
import com.backend.DuruDuru.global.repository.TownRepository;
import com.backend.DuruDuru.global.web.dto.Town.TownRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TownCommandServiceImpl implements TownCommandService {

    private final TownRepository townRepository;
    private final MemberRepository memberRepository;

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    // Town 엔티티를 저장하는 메서드
    @Override
    @Transactional
    public Town createTown(Long memberId, TownRequestDTO.ToTownRequestDTO request) {
        Member member =  findMemberById(memberId);
        if(member.getTown() != null) {
            throw new TownHandler(ErrorStatus.TOWN_ALREADY_EXISTS);
        }

        Town newTown = TownConverter.toCreateTown(request, member);
        member.setTown(newTown);
        return townRepository.save(newTown);
    }

    // Town 정보를 수정하는 메서드
    @Override
    @Transactional
    public Town updateTown(Long memberId, TownRequestDTO.ToTownRequestDTO request) {
        Member member =  findMemberById(memberId);
        if(member.getTown() == null) {
            throw new TownHandler(ErrorStatus.TOWN_NOT_REGISTERED);
        }

        Town currentTown = member.getTown();
        currentTown.update(request);
        return townRepository.save(member.getTown());
    }
}
