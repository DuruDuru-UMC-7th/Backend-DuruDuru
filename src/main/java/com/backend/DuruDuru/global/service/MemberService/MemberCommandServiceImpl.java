package com.backend.DuruDuru.global.service.MemberService;

import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.exception.handler.MemberException;
import com.backend.DuruDuru.global.converter.MemberConverter;
import com.backend.DuruDuru.global.domain.entity.Fridge;
import com.backend.DuruDuru.global.domain.entity.Member;
import com.backend.DuruDuru.global.repository.FridgeRepository;
import com.backend.DuruDuru.global.repository.MemberRepository;
import com.backend.DuruDuru.global.security.provider.JwtTokenProvider;
import com.backend.DuruDuru.global.web.dto.Member.MemberRequestDTO;
import com.backend.DuruDuru.global.web.dto.Member.MemberResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService{

    private final MemberRepository memberRepository;
    private final FridgeRepository fridgeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Member findMemberById(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void emailRegister(MemberRequestDTO.EmailRegisterRequestDTO request) {
        // 이메일 중복 검사
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberException(ErrorStatus.MEMBER_EMAIL_ALREADY_EXISTS);
        }

        // 회원 생성
        Member member = Member.builder()
                .nickName(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        memberRepository.save(member);

        // 회원 가입시 자동 개인 냉장고 생성
        Fridge fridge = Fridge.builder()
                .ingredientCount(0)
                .build();
        fridge.setMember(member);
        member.setFridge(fridge);
        fridgeRepository.save(fridge);


    }


    @Override
    @Transactional
    public MemberResponseDTO.EmailLoginResponseDTO emailLogin(MemberRequestDTO.EmailLoginRequestDTO request) {
        // 이메일로 사용자 조회
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_EMAIL_ALREADY_EXISTS));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new MemberException(ErrorStatus.MEMBER_LOGIN_FAIL);
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(member.getMemberId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId());

        // DB에 refresh token 저장
        member.setRefreshToken(refreshToken);
        member.setAccessToken(accessToken);
        memberRepository.save(member);


        return MemberConverter.toEmailLoginResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public MemberResponseDTO.TokenRefreshResponseDTO refreshToken(String refreshToken) {
        jwtTokenProvider.isTokenValid(refreshToken);
        Long id = jwtTokenProvider.getId(refreshToken);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        String newAccessToken = jwtTokenProvider.createAccessToken(id);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(id);
        member.updateToken(newAccessToken, newRefreshToken);
        memberRepository.save(member);
        return MemberConverter.toTokenRefreshResponse(newAccessToken, newRefreshToken);
    }
}
