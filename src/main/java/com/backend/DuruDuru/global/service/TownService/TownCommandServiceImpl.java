package com.backend.DuruDuru.global.service.TownService;


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
                .orElseThrow(() -> new IllegalArgumentException("Member not found. ID: " + memberId));
    }

    // Town 엔티티를 저장하는 메서드
    @Override
    @Transactional
    public Town createTown(Long memberId, TownRequestDTO.ToTownRequestDTO request) {
        Member member =  findMemberById(memberId);

        Town newTown = TownConverter.toCreateTown(request, member);
        newTown.setMember(member);

        return townRepository.save(newTown);
    }

    // Town 정보를 수정하는 메서드
    @Override
    @Transactional
    public Town updateTown(Long memberId, TownRequestDTO.ToTownRequestDTO request) {
        Member member =  findMemberById(memberId);
        Town currentTown = member.getTown();

        if(currentTown == null) {
            throw new IllegalArgumentException("Member doesn't have town.");
        }
        currentTown.update(request);
        Town updatedTown = member.getTown();

        return townRepository.save(updatedTown);
    }
}
